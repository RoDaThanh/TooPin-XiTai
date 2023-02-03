package com.toopinxitai.productservice.controller;

import com.toopinxitai.productservice.dto.ProductRequest;
import com.toopinxitai.productservice.dto.ProductRespone;
import com.toopinxitai.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product")

public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductRespone> getAllProducts(){
        return productService.getAllProducts();
    }
}
