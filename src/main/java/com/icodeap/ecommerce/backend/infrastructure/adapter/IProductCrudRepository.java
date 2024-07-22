package com.icodeap.ecommerce.backend.infrastructure.adapter;

import com.icodeap.ecommerce.backend.infrastructure.entity.ProductEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IProductCrudRepository extends CrudRepository<ProductEntity, Integer> {
	
	@Query("SELECT p FROM ProductEntity p WHERE p.id IN :ids")
	List<ProductEntity> findByProductsId(@Param("ids") List<Integer> idProducts);
	
	@Query("SELECT p FROM ProductEntity p WHERE p.categoryEntity.id = :category"
			+ " and lower(concat(p.name,' ',p.description))"
			+ " like lower(concat('%',:name,'%'))")
	List<ProductEntity> findByCategoryName(@Param("category") Integer category,@Param("name") String name);
	
	@Query("SELECT p FROM ProductEntity p WHERE "
			+ " lower(concat(p.name,' ',p.description)) like lower(concat('%',:name,'%'))")
	List<ProductEntity> findByName(@Param("name") String name);
}
