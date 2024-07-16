package com.icodeap.ecommerce.backend.domain.port;

import java.util.List;

import com.icodeap.ecommerce.backend.domain.model.Product;

public interface IProductRepository {
    Product save (Product product);
    Iterable<Product> findAll();
    Product findById(Integer id);
    void deleteById(Integer id);
    Iterable<Product> findByProductsId(List<Integer> idsProducts);
}
