package com.icodeap.ecommerce.backend.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ModeloCategory {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
