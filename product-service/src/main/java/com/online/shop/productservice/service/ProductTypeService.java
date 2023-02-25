package com.online.shop.productservice.service;

import com.online.shop.productservice.dto.ProductTypeRequest;
import com.online.shop.productservice.dto.ProductTypeResponse;
import com.online.shop.productservice.model.ProductType;
import com.online.shop.productservice.repository.ProductTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    public ProductTypeResponse createProductType(ProductTypeRequest productTypeRequest) {
        ProductType productType = ProductType.builder()
                .name(productTypeRequest.getName())
                .description(productTypeRequest.getDescription())
                .img(productTypeRequest.getImg())
                .build();
        log.info("New Product Type {} was created!", productType.getId());
        return mapToProductTypeRespone(productTypeRepository.save(productType));
    }

    public List<ProductTypeResponse> getAllProductTypes(){
        List<ProductType> productTypes = productTypeRepository.findAll();
        return productTypes.stream().map(this::mapToProductTypeRespone).collect(Collectors.toList());
    }

    public ProductTypeResponse getProductTypeById(String productTypeId) {
        Optional<ProductType> productType = productTypeRepository.findById(productTypeId);

        if(productType.isPresent())
            return mapToProductTypeRespone(productType.get());

        throw new IllegalArgumentException("No product type is found with product type id: "+ productTypeId );
    }

    private ProductTypeResponse mapToProductTypeRespone(ProductType productType) {
        return ProductTypeResponse.builder().id(productType.getId())
                .name(productType.getName())
                .img(productType.getImg())
                .description(productType.getDescription()).build();
    }
}
