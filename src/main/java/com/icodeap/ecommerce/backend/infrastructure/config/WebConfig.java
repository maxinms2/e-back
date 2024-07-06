//package com.icodeap.ecommerce.backend.infrastructure.config;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.icodeap.ecommerce.backend.infrastructure.entity.ParameterEntity;
//import com.icodeap.ecommerce.backend.infrastructure.service.ParameterService;
//
////WebConfig.java
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
// @Autowired
// private ParameterService service;
//
// @Override
// public void addCorsMappings(CorsRegistry registry) {
//	 Optional<ParameterEntity> parameter = service.getParameter(null);
//
//     registry.addMapping("/**")
//             .allowedOrigins(parameter.get().getValor().split(","))
//             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Valores por defecto
//             .allowedHeaders("*") // Valores por defecto
//             .exposedHeaders("*") // Valores por defecto
//             .allowCredentials(true) // Valores por defecto
//             .maxAge(3600); // Valores por defecto
// }
//}
//
