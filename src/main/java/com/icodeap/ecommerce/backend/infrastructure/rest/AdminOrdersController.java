package com.icodeap.ecommerce.backend.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icodeap.ecommerce.backend.application.OrderService;
import com.icodeap.ecommerce.backend.domain.model.Constants;
import com.icodeap.ecommerce.backend.domain.model.Order;
import com.icodeap.ecommerce.backend.domain.model.OrderDTO;
import com.icodeap.ecommerce.backend.domain.model.OrderState;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/admin/orders")
//@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AdminOrdersController {

    private final OrderService orderService;

    public AdminOrdersController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("/by-status/{orderState}")
    public ResponseEntity<Iterable<OrderDTO>> findByUserId(@PathVariable("orderState") OrderState orderState){
        return ResponseEntity.ok(orderService.findByOrderState(orderState));
    }
    
    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderDTO order){
    	
    	
//    	String status=order.getOrderState().toString();
//        if (status.equals(OrderState.CANCELLED.toString()) ){
//        	orderBD.setOrderState(Constants.CANCELLED);
//        }else if(status.equals(OrderState.CONFIRMED.toString()) ) {
//        	orderBD.setOrderState(Constants.CONFIRMED);
//        }
//        else{
//        	orderBD.setOrderState(Constants.DELIVERED);
//        }
    	if(order.getId()!=null && order.getId()>0) {
    		return ResponseEntity.ok(orderService.update(order));
    	}else {
    		return ResponseEntity.ok(orderService.save(order));
    	}

        
    }
}
