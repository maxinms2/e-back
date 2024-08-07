package com.icodeap.ecommerce.backend.infrastructure.adapter;

import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.port.IProductRepository;
import com.icodeap.ecommerce.backend.infrastructure.mapper.ProductMapper;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProductCrudRepositoryImpl implements IProductRepository {
    private final IProductCrudRepository iProductCrudRepository;
    private final ProductMapper productMapper;



    @Override
    public Product save(Product product) {
        return productMapper.toProduct(iProductCrudRepository.save(productMapper.toProductEntity(product)));
    }

    @Override
    public Iterable<Product> findAll() {
        return productMapper.toProductList(iProductCrudRepository.findAll());
    }

    @Override
    public Product findById(Integer id) {
        return productMapper.toProduct(iProductCrudRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Producto con id: "+id+" no existe")
        ) );
    }

    @Override
    public void deleteById(Integer id) {
        iProductCrudRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Producto con id: "+id+" no existe")
        );
        iProductCrudRepository.deleteById(id);
    }

	@Override
	public Iterable<Product> findByProductsId(List<Integer> idsProducts) {

		return productMapper.toProductList(iProductCrudRepository.findByProductsId(idsProducts));
	}

	@Override
	public Iterable<Product> findByCategoryName(Integer category, String name) {
		
		return productMapper.toProductList(iProductCrudRepository.findByCategoryName(category,name));
	}

	@Override
	public Iterable<Product> findByName(String name) {

		return productMapper.toProductList(iProductCrudRepository.findByName(name));
	}
}
