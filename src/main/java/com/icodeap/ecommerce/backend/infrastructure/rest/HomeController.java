package com.icodeap.ecommerce.backend.infrastructure.rest;

import com.icodeap.ecommerce.backend.application.CategoryService;
import com.icodeap.ecommerce.backend.application.ProductService;
import com.icodeap.ecommerce.backend.domain.model.Category;
import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.model.ProductHomeRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
//@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService,CategoryService categoryService) {
        this.productService = productService;
		this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }
    
    @PostMapping
    public ResponseEntity<Iterable<Product>> findAllCategoryName(@RequestBody ProductHomeRequest request){
        return ResponseEntity.ok(productService.findByCategoryName(request.getCategory(),request.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }
    
    @GetMapping("/categories")
    public ResponseEntity<Iterable<Category>>findAllCategory(){
        return  ResponseEntity.ok(categoryService.findAll()) ;
    }
}
