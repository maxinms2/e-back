package com.icodeap.ecommerce.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private String code;
    private String description;
    private String urlImage;
    private BigDecimal price;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private  Integer userId;
    private Category category;
    private Integer categoryId;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}
    
    
}
