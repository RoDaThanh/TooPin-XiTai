package com.online.shop.productservice.controller;

import com.online.shop.productservice.dto.ProductRespone;
import com.online.shop.productservice.dto.ProductTypeRequest;
import com.online.shop.productservice.dto.ProductTypeResponse;
import com.online.shop.productservice.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "product-service/api/categories")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductTypeResponse createProductType(@RequestBody ProductTypeRequest productTypeRequest){
        return productTypeService.createProductType(productTypeRequest);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductTypeResponse> getAllProductTypes(){
        return productTypeService.getAllProductTypes();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductTypeResponse getProductTypes(@PathVariable("id") String productTypeId){
        return productTypeService.getProductTypeById(productTypeId);
    }

}
