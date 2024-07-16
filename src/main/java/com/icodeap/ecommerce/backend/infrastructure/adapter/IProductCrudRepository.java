package com.icodeap.ecommerce.backend.infrastructure.adapter;

import com.icodeap.ecommerce.backend.infrastructure.entity.ProductEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IProductCrudRepository extends CrudRepository<ProductEntity, Integer> {
	
	@Query("SELECT p FROM ProductEntity p WHERE p.id IN :ids")
	List<ProductEntity> findByProductsId(@Param("ids") List<Integer> idProducts);
}
