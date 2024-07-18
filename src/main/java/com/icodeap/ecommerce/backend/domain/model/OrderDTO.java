package com.icodeap.ecommerce.backend.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
//@AllArgsConstructor
//@NoArgsConstructor
@Data
public class OrderDTO {
    private Integer id;
    private LocalDateTime dateCreated;
    private List<OrderProductDTO> orderProducts;
    private OrderState orderState;
    private Integer userId;
    private String userName;
    private String email;
    
    
	public OrderDTO(Integer id, LocalDateTime dateCreated, List<OrderProductDTO> orderProducts,
			OrderState orderState,Integer userId,String userName,String email) {
		this.id = id;
		this.dateCreated = dateCreated;
		this.orderProducts = orderProducts;
		this.orderState = orderState;
		this.userId = userId;
		this.userName=userName;
		this.email=email;
	}


	public OrderDTO() {
	}
    
    
}
