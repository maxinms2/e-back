package com.icodeap.ecommerce.backend.domain.model;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class OrderProductDTO {
    private Integer id;
    private BigDecimal quantity;
    private BigDecimal price;
    private Integer productId;
    private String name;
    private String description;
    private String model;
}
