package com.online.shop.productservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.util.List;

@Document(value = "product_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductType {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String description;

    private String img;

    @ReadOnlyProperty
    @DocumentReference(lookup="{'product_type_id':?#{#self._id} }")
    List<Product> products;
}
