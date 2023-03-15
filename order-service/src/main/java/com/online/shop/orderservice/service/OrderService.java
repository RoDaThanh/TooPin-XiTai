package com.online.shop.orderservice.service;

import com.online.shop.orderservice.config.WebclientConfig;
import com.online.shop.orderservice.dto.InventoryResponse;
import com.online.shop.orderservice.dto.OrderItemRequest;
import com.online.shop.orderservice.dto.OrderRequest;
import com.online.shop.orderservice.model.Order;
import com.online.shop.orderservice.model.OrderItem;
import com.online.shop.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) {
        List<OrderItem> items = orderRequest.getOrderItemRequestList().stream()
                .map(this::mapToOrderItem).collect(Collectors.toList());
        Order order = Order.builder().orderItems(items)
                .orderNumber(UUID.randomUUID().toString())
                .build();
        List<String> skuCodes = orderRequest.getOrderItemRequestList().stream().
                map(OrderItemRequest::getSkuCode).collect(Collectors.toList());

        //TODO check sku code exist or not
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("sku-code", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class).block();
        boolean isAllInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (isAllInStock)
            orderRepository.save(order);
        else
             Problem.valueOf(Status.BAD_REQUEST, "Is not in Stock");
    }

    private OrderItem mapToOrderItem(OrderItemRequest itemRequest) {
        return OrderItem.builder().skuCode(itemRequest.getSkuCode())
                .price(itemRequest.getPrice())
                .quantity(itemRequest.getQuantity())
                .build();
    }

}
