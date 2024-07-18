package com.icodeap.ecommerce.backend.infrastructure.rest.request;

import com.icodeap.ecommerce.backend.domain.model.OrderState;

import lombok.Data;

@Data
public class NameMailRequest {
	private OrderState orderState;
	private String fullName;
	private String email; 
	private Integer page;
	private Integer pageSize;
}
