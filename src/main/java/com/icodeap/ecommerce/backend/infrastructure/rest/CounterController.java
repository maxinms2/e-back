package com.icodeap.ecommerce.backend.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icodeap.ecommerce.backend.infrastructure.service.ParameterService;

@RestController
@RequestMapping("/api/v1/counter")
public class CounterController {
	@Autowired
	private ParameterService service;

	@GetMapping("/set")
	public ResponseEntity<String> save(){
		return ResponseEntity.ok(service.saveCounter());
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> get(){
		return ResponseEntity.ok(service.getCounter());
	}
}
