package com.icodeap.ecommerce.backend.infrastructure.config;

import com.icodeap.ecommerce.backend.application.*;
import com.icodeap.ecommerce.backend.domain.port.ICategoryRepository;
import com.icodeap.ecommerce.backend.domain.port.IMailSenderService;
import com.icodeap.ecommerce.backend.domain.port.IOrderRepository;
import com.icodeap.ecommerce.backend.domain.port.IProductRepository;
import com.icodeap.ecommerce.backend.domain.port.IUserRepository;
import com.icodeap.ecommerce.backend.infrastructure.service.MailSenderBrevoService;
import com.icodeap.ecommerce.backend.infrastructure.service.ParameterService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {
    @Bean
    public UserService userService(IUserRepository iUserRepository){
        return new UserService(iUserRepository);
    }

    @Bean
    public CategoryService categoryService(ICategoryRepository iCategoryRepository){
        return new CategoryService(iCategoryRepository);
    }

    @Bean
    public ProductService productService(IProductRepository iProductRepository, UploadFile uploadFile
    		, ICategoryRepository icategoryRepository){
        return  new ProductService(iProductRepository, uploadFile,icategoryRepository);
    }
    @Bean
    public OrderService orderService(IOrderRepository iOrderRepository,
    		IMailSenderService mailSender,MailOrder mailorder,IProductRepository iProductRepository,
    		IUserRepository iuserRepository){
        return new OrderService(iOrderRepository,mailSender,mailorder,iProductRepository,iuserRepository);
    }
    
    @Bean
    public MailOrder getMailOrder(IUserRepository userRepository,IProductRepository productRepository) {
    	return new MailOrder(userRepository,productRepository);
    }
    @Bean
    public UploadFile uploadFile(ParameterService parameterService){
        return new UploadFile(parameterService);
    }

    @Bean
    public RegistrationService registrationService(IUserRepository iUserRepository){
        return new RegistrationService(iUserRepository);
    }
    
    @Bean
    public IMailSenderService mailSender() {
    	return new MailSenderBrevoService();
    }
    
    @Bean
    public ParameterService parameterService() {
    	return new ParameterService();
    }
    
    @Bean
    public ReportProductService reportProduct(IProductRepository iProductRepository, 
    		ICategoryRepository icategoryRepository) {
    	return new ReportProductService(iProductRepository, icategoryRepository);
    }

}
