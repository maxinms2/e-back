package com.icodeap.ecommerce.backend.application;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.icodeap.ecommerce.backend.infrastructure.entity.ParameterEntity;
import com.icodeap.ecommerce.backend.infrastructure.service.ParameterService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadFile {
    //private final String FOLDER = "src//main//resources//static//images//";
    private final String FOLDER = System.getProperty("user.home")+"/files/images/";
    private final String IMG_DEFAULT = "default.jpg";
    private final String URL; //= "http://localhost:8085/images/";
    private ProductService productService;
    
    public UploadFile(ParameterService parameterService) {
    	this.productService=productService;
    	ParameterEntity parameter=parameterService.getParameter("connection.urlback").get();
    	URL=parameter.getValor()+"/images/";
    	
    }

    public String upload (MultipartFile multipartFile) throws IOException {
        if (multipartFile!=null){
            byte [] bytes = multipartFile.getBytes();
            Path path = Paths.get(FOLDER+multipartFile.getOriginalFilename());
            System.out.println("FOLDER: "+FOLDER);
            System.out.println("URL: "+URL);
            Files.write(path, bytes);
            return URL+multipartFile.getOriginalFilename();
        }
        return  URL+IMG_DEFAULT;
    }
    public void delete(String nameFile){
        File file = new File(FOLDER+nameFile);
        file.delete();
    }
    

}
