package com.online.shop.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTypeResponse {
    private String id;
    @Indexed(unique = true)
    private String name;

    private String description;

    private String img;
}
