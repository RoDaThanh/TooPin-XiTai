package com.online.shop.productservice.controller;

import com.online.shop.productservice.dto.ProductRequest;
import com.online.shop.productservice.dto.ProductRespone;
import com.online.shop.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "product-service/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRespone createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductRespone> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductRespone getProduct(@PathVariable("id") String productId){
        return productService.getProductById(productId);
    }

}
