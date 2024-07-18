package com.icodeap.ecommerce.backend.infrastructure.adapter;

import com.icodeap.ecommerce.backend.domain.model.OrderState;
import com.icodeap.ecommerce.backend.infrastructure.entity.OrderEntity;
import com.icodeap.ecommerce.backend.infrastructure.entity.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface IOrderCrudRepository extends JpaRepository<OrderEntity, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o SET o.orderState = :state WHERE o.id = :id")
    void updateStateById(Integer id, OrderState state);

    Iterable<OrderEntity> findByUserEntity(UserEntity userEntity);
    
    Iterable<OrderEntity> findAllByOrderByDateCreatedAsc();
    
    Iterable<OrderEntity> findByOrderStateOrderByDateCreatedAsc(Integer orderState);
    
    @Query("SELECT o FROM OrderEntity o WHERE o.orderState = ?1 and"
    		+ " LOWER(CONCAT(o.userEntity.firstName, ' ', o.userEntity.lastName)) "
    		+ "LIKE LOWER(CONCAT(?2,'%')) "
    		+ "AND LOWER(o.userEntity.email) LIKE LOWER(CONCAT(?3,'%')) ORDER BY o.dateCreated ASC")
//    @Query("SELECT o FROM OrderEntity o WHERE o.orderState = ?1 and"
//    		+ " o.userEntity.firstName = ?2")
    Page<OrderEntity> findByFullNameAndEmail(Integer orderState, String fullName, String email, Pageable pageable);
}
