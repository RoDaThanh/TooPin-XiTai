package com.online.shop.productservice.service;

import com.online.shop.productservice.dto.ProductRequest;
import com.online.shop.productservice.dto.ProductRespone;
import com.online.shop.productservice.model.Product;
import com.online.shop.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository ;

    public ProductRespone createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .img(productRequest.getImg())
                .productTypeId(productRequest.getProductTypeId())
                .build();
        productRepository.save(product);
        log.info("Product {} was created!", product.getId());
        return mapToProductRespone(product);
    }

    public List<ProductRespone> getProducts(String categoryId){
        if(StringUtils.isBlank(categoryId))
            return getAllProducts();
        return getProductsByCategoryId(categoryId);
    }

    private List<ProductRespone> getProductsByCategoryId(String categoryId) {
        List<Product> products = productRepository.findByProductTypeId(categoryId);
        if (products == null) {
            throw Problem.valueOf(Status.BAD_REQUEST, "Cannot find any product with categoryId: "+ categoryId);
        }
        return products.stream().map(this::mapToProductRespone).collect(Collectors.toList());
    }

    private List<ProductRespone> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products == null) {
            return new ArrayList<>();
        }
        return products.stream().map(this::mapToProductRespone).collect(Collectors.toList());
    }

    private ProductRespone mapToProductRespone(Product product) {
        return ProductRespone.builder().id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .img(product.getImg())
                .productTypeId(product.getProductTypeId())
                .description(product.getDescription()).build();
    }


    public ProductRespone getProductById(String productId) {
        Optional<Product> product = productRepository.findById(productId);

        if(product.isPresent())
            return mapToProductRespone(product.get());

        throw  Problem.valueOf(Status.NOT_FOUND,"No product is found with product id: "+ productId );
    }
}
