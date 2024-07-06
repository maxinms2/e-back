package com.icodeap.ecommerce.backend.application;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.icodeap.ecommerce.backend.infrastructure.service.ParameterService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadFile {
    //private final String FOLDER = "src//main//resources//static//images//";
    private final String FOLDER = "C:/files/images/";
    private final String IMG_DEFAULT = "default.jpg";
    private final String URL = "http://localhost:8085/images/";
    
    private final ParameterService parameterService;
    
    

    public UploadFile(ParameterService parameterService) {
		this.parameterService = parameterService;
	}
	public String upload (MultipartFile multipartFile) throws IOException {
        if (multipartFile!=null){
            byte [] bytes = multipartFile.getBytes();
            Path path = Paths.get(FOLDER+multipartFile.getOriginalFilename());
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
