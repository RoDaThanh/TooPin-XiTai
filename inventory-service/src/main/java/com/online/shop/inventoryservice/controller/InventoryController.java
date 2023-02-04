package com.online.shop.inventoryservice.controller;

import com.online.shop.inventoryservice.dto.InventoryRequest;
import com.online.shop.inventoryservice.dto.InventoryResponse;
import com.online.shop.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getInventoriesBySkucodes(@RequestParam("sku-code") List<String> skuCodes){
        return inventoryService.getInventoryBySkuCodes(skuCodes);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addStock(@RequestBody InventoryRequest inventoryRequest){
         inventoryService.addStock(inventoryRequest);
    }
}
