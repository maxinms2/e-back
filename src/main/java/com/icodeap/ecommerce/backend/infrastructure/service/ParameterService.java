package com.icodeap.ecommerce.backend.infrastructure.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icodeap.ecommerce.backend.infrastructure.adapter.ParameterRepository;
import com.icodeap.ecommerce.backend.infrastructure.entity.ParameterEntity;

@Service
public class ParameterService {
	@Autowired
	private ParameterRepository repository;
	
	public Optional<ParameterEntity> getParameter(String clave) {
		return repository.findByClave(clave);
	}
	
	public ParameterEntity save(ParameterEntity parameter) {
		return repository.save(parameter);
	}

}
