package com.toopinxitai.productservice.service;

import com.toopinxitai.productservice.dto.ProductRequest;
import com.toopinxitai.productservice.dto.ProductRespone;
import com.toopinxitai.productservice.model.Product;
import com.toopinxitai.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} was created!", product.getId());
    }

    public List<ProductRespone> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductRespone).collect(Collectors.toList());
    }

    private ProductRespone mapToProductRespone(Product product) {
        return ProductRespone.builder().id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription()).build();
    }


}
