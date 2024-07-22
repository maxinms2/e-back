package com.icodeap.ecommerce.backend.application;

import com.icodeap.ecommerce.backend.domain.model.Category;
import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.port.ICategoryRepository;
import com.icodeap.ecommerce.backend.domain.port.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Slf4j
public class ProductService {
    private final IProductRepository iProductRepository;
    private final UploadFile uploadFile;
    private final ICategoryRepository icategoryRepository;

    public ProductService(IProductRepository iProductRepository, UploadFile uploadFile,
    		ICategoryRepository icategoryRepository) {
        this.iProductRepository = iProductRepository;
        this.uploadFile = uploadFile;
        this.icategoryRepository=icategoryRepository;
    }

    public Product save(Product product, MultipartFile multipartFile) throws IOException {
        if(product.getId()!=0){//cuando es un producto modificado
            if(multipartFile==null){
                product.setUrlImage(product.getUrlImage());
            }else{
                String nameFile = product.getUrlImage().substring(29);
                log.info("este es el nombre de la imagen: {}", nameFile);
                if (!nameFile.equals("default.jpg")){
                    uploadFile.delete(nameFile);
                }
                product.setUrlImage(uploadFile.upload(multipartFile));
            }
        }else{
            product.setUrlImage(uploadFile.upload(multipartFile));
        }
        
        
        Category category=icategoryRepository.findById(product.getCategoryId());
        product.setCategory(category);
        return this.iProductRepository.save(product);
    }

    public Iterable<Product> findAll(){
    	Random random=new Random();
    	List<Product> products=(List<Product>)this.iProductRepository.findAll();
    	List<Product> productsRdm = getProductsOrderRandom(products);
        return productsRdm;
    }
    
    public Iterable<Product> findByCategoryName(Integer category,String name){
    	List<Product> products=null;
    	if(category>0) {
    		products=(List<Product>)this.iProductRepository.findByCategoryName(category, name);
    	}else {
    		products=(List<Product>)this.iProductRepository.findByName(name);
    	}
    	List<Product> productsRdm = getProductsOrderRandom(products);
        return productsRdm;
    }

	private List<Product> getProductsOrderRandom(List<Product> products) {
		Random random=new Random();
		List<Product> copyProducts=new ArrayList<>(products);
    	List<Product> productsRdm=new ArrayList<>();
    	while(!copyProducts.isEmpty()) {
    		int rdmIndex = random.nextInt(copyProducts.size());
    		Product productRdm = copyProducts.remove(rdmIndex);
    		productsRdm.add(productRdm);
    	}
		return productsRdm;
	}

    public Product findById(Integer id){
    	Product product=this.iProductRepository.findById(id);
    	product.setCategoryId(product.getCategory().getId());
        return product;
    }
    public void deleteById(Integer id){
        Product product = findById(id);
        String nameFile = product.getUrlImage().substring(29);
        log.info("este es el nombre de la imagen: {}", nameFile);
        if (!nameFile.equals("default.jpg")){
            uploadFile.delete(nameFile);
        }
        this.iProductRepository.deleteById(id);
    }
}
