package com.icodeap.ecommerce.backend.infrastructure.rest;

import com.icodeap.ecommerce.backend.application.OrderService;
import com.icodeap.ecommerce.backend.domain.model.Order;
import com.icodeap.ecommerce.backend.domain.model.OrderState;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
//@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Order order){
        System.out.println(order.getOrderState());
        if(order.getOrderProducts()==null || order.getOrderProducts().isEmpty()){
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Elija productos para procesar su orden.");
        }
        if (order.getOrderState().toString().equals(OrderState.CANCELLED.toString()) ){
            order.setOrderState(OrderState.CANCELLED);
        }else if(order.getOrderState().toString().equals(OrderState.PROGRESS.toString()) ) {
        	order.setOrderState(OrderState.PROGRESS);
        }
        else{
            order.setOrderState(OrderState.CONFIRMED);
        }

        return ResponseEntity.ok(orderService.save(order));
    }

    @PostMapping("/update/state/order")
    public ResponseEntity updateStateById(@RequestParam Integer id, @RequestParam String state){
        orderService.updateStateById(id, state);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Order>> findAll(){
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("{variable}")
    public ResponseEntity<Order> findById(@PathVariable("variable") Integer id){
        return  ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/by-user/{id}")
    public ResponseEntity<Iterable<Order>> findByUserId(@PathVariable("id") Integer userId){
        return ResponseEntity.ok(orderService.findByUserId(userId));
    }

}
