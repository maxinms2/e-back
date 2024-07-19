package com.icodeap.ecommerce.backend.infrastructure.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icodeap.ecommerce.backend.application.ReportProductService;
import com.icodeap.ecommerce.backend.domain.model.FileReport;

@RestController
//http://localhost:8085
@RequestMapping("/api/v1/reports/products")
//http://localhost:8085/api/v1/users
//@CrossOrigin(origins = "http://localhost:4200")
public class ReportProductController {

	private final ReportProductService service;

	public ReportProductController(ReportProductService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<FileReport> exportUsersToExcel() {
		try {

			return new ResponseEntity<>(service.exportProductsToExcel(), HttpStatus.OK);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
