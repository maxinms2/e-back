package com.icodeap.ecommerce.backend.domain.port;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.infrastructure.entity.ProductEntity;

public interface IProductRepository {
    Product save (Product product);
    Iterable<Product> findAll();
    Iterable<Product> findByCategoryName(Integer category,String name);
    Product findById(Integer id);
    void deleteById(Integer id);
    Iterable<Product> findByProductsId(List<Integer> idsProducts);
    Iterable<Product> findByName(String name);
}
