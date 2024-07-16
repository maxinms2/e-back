package com.icodeap.ecommerce.backend.domain.utils;

import java.util.ArrayList;
import java.util.List;

import com.icodeap.ecommerce.backend.domain.model.Constants;
import com.icodeap.ecommerce.backend.domain.model.Order;
import com.icodeap.ecommerce.backend.domain.model.OrderDTO;
import com.icodeap.ecommerce.backend.domain.model.OrderProduct;
import com.icodeap.ecommerce.backend.domain.model.OrderProductDTO;
import com.icodeap.ecommerce.backend.domain.model.OrderState;

public class ConvertersOrder {
    public Integer getStatusIntFromEnum(OrderState orderState){
    	if(orderState.equals(OrderState.CANCELLED)) {
    		return Constants.CANCELLED;
    	}
    	if(orderState.equals(OrderState.CONFIRMED)) {
    		return Constants.CONFIRMED;
    	}
    	if(orderState.equals(OrderState.DELIVERED)) {
    		return Constants.DELIVERED;
    	}
    	return Constants.DELIVERED;
    }
    
    public OrderState getStatusEnumFromInt(Integer orderState){
    	if(orderState.equals(Constants.CANCELLED)) {
    		return OrderState.CANCELLED;
    	}
    	if(orderState.equals(Constants.CONFIRMED)) {
    		return OrderState.CONFIRMED;
    	}
    	if(orderState.equals(Constants.DELIVERED)) {
    		return OrderState.DELIVERED;
    	}
    	return OrderState.DELIVERED;
    }
    
    public List<OrderProduct> convertOrderProductsDtoToProducts(List<OrderProductDTO> productsDTO){
    	List<OrderProduct> orderProducts=new ArrayList<>();
    	productsDTO.forEach(p->{
    		OrderProduct op=new OrderProduct();
    		op.setId(p.getId());
    		op.setModel(p.getModel());
    		op.setPrice(p.getPrice());
    		op.setProductId(p.getProductId());
    		op.setQuantity(p.getQuantity());
    		orderProducts.add(op);
    	});
    	return orderProducts;
    }
    
    public Order getOrderFromOrderDTO(OrderDTO orderDTO) {
    	Order order=new Order();
    	order.setDateCreated(orderDTO.getDateCreated());
    	order.setOrderProducts(convertOrderProductsDtoToProducts(orderDTO.getOrderProducts()));
    	order.setOrderState(getStatusIntFromEnum(orderDTO.getOrderState()));
    	order.setUserId(orderDTO.getUserId());
    	return order;
    }
    
	public List<OrderProductDTO> convertProductsOrderToProductsDTO(Order order) {
		List<OrderProductDTO> orderProductsDTO=new ArrayList<>();
    	order.getOrderProducts().forEach(o->{
    		OrderProductDTO dto=new OrderProductDTO();
    		dto.setId(o.getId());
    		dto.setDescription(o.getProductId().toString());
    		dto.setModel(o.getModel());
    		dto.setPrice(o.getPrice());
    		dto.setProductId(o.getProductId());
    		dto.setQuantity(o.getQuantity());
    		orderProductsDTO.add(dto);
    	});
		return orderProductsDTO;
	}
	
	public OrderDTO convertOrderDTOFromOrder(Order order) {
		OrderDTO orderDTO=new OrderDTO();
		orderDTO.setDateCreated(order.getDateCreated());
		orderDTO.setId(order.getId());
		orderDTO.setUserId(order.getUserId());
		orderDTO.setOrderProducts(convertProductsOrderToProductsDTO(order));
		orderDTO.setOrderState(getStatusEnumFromInt(order.getOrderState()));
		return orderDTO;
	}

}
