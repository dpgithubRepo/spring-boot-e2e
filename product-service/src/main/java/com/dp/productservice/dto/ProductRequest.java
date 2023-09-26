package com.dp.productservice.dto;


import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
}
