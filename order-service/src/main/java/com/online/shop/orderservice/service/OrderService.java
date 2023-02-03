package com.online.shop.orderservice.service;

import com.online.shop.orderservice.dto.OrderItemRequest;
import com.online.shop.orderservice.dto.OrderRequest;
import com.online.shop.orderservice.model.Order;
import com.online.shop.orderservice.model.OrderItem;
import com.online.shop.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        List<OrderItem> items = orderRequest.getOrderItemRequestList().stream()
                .map(itemDto-> mapToOrderItem(itemDto)).collect(Collectors.toList());
        Order order = Order.builder().orderItems(items)
                .orderNumber(UUID.randomUUID().toString())
                .build();

        orderRepository.save(order);
    }

    private OrderItem mapToOrderItem(OrderItemRequest itemRequest) {
        return OrderItem.builder().skuCode(itemRequest.getSkuCode())
                .price(itemRequest.getPrice())
                .quantity(itemRequest.getQuantity())
                .build();
    }

}
