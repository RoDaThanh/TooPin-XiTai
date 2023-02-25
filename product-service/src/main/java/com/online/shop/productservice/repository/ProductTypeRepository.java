package com.online.shop.productservice.repository;
import com.online.shop.productservice.model.ProductType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends MongoRepository<ProductType, String> {
}
