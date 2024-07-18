package com.icodeap.ecommerce.backend.domain.port;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.icodeap.ecommerce.backend.domain.model.Order;

public interface IOrderRepository {
    Order save (Order order);
    Order findById (Integer id);
    Iterable<Order> findAll();
    Iterable<Order> findByUserId(Integer userId);
    Iterable<Order> findByOrderState(Integer orderState);
    void updateStateById(Integer id, String state);
    Page<Order> findByFullNameAndEmail(Integer orderState, String fullName, String email, Pageable pageable);
}
