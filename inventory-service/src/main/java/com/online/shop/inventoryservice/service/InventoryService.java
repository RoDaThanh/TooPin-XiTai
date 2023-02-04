package com.online.shop.inventoryservice.service;

import com.online.shop.inventoryservice.dto.InventoryRequest;
import com.online.shop.inventoryservice.dto.InventoryResponse;
import com.online.shop.inventoryservice.model.Inventory;
import com.online.shop.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public List<InventoryResponse> getInventoryBySkuCodes(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes).get().stream()
                .map(inventory -> mapToInventoryResponse(inventory)).collect(Collectors.toList());
    }

    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder().skuCode(inventory.getSkuCode())
                .isInStock(inventory.getQuantity() > 0).build();
    }

    public void addStock(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build();
        inventoryRepository.save(inventory);
        log.info("Added {}", inventory.getSkuCode());
    }
}
