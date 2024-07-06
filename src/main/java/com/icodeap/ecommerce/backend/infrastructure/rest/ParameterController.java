package com.icodeap.ecommerce.backend.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icodeap.ecommerce.backend.infrastructure.entity.ParameterEntity;
import com.icodeap.ecommerce.backend.infrastructure.service.ParameterService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/admin/parameters")
//@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class ParameterController {
	
	@Autowired
	private ParameterService service;

	@PostMapping
	public ResponseEntity<ParameterEntity> save(@RequestBody ParameterEntity parameter){
		return ResponseEntity.ok(service.save(parameter));
	}
	
	@GetMapping
	public ResponseEntity<ParameterEntity> get(@RequestBody String parameter){
		return ResponseEntity.ok(service.getParameter(parameter).get());
	}
	

}
