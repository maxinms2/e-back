package com.icodeap.ecommerce.backend.infrastructure.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icodeap.ecommerce.backend.infrastructure.adapter.ParameterRepository;
import com.icodeap.ecommerce.backend.infrastructure.entity.ParameterEntity;

public class ParameterService {
	@Autowired
	private ParameterRepository repository;
	
	public Optional<ParameterEntity> getParameter(String clave) {
		return repository.findByClave(clave);
	}
	
	public ParameterEntity save(ParameterEntity parameter) {
		return repository.save(parameter);
	}

	public String saveCounter() {
		Optional<ParameterEntity> parameter=getParameter("visits.number");
		if(parameter.isPresent()) {
			Integer valor=Integer.parseInt(parameter.get().getValor());
			valor++;
			parameter.get().setValor(valor.toString());
			return repository.save(parameter.get()).getValor();
		}
		return "";
	}
	
	public String getCounter() {
		Optional<ParameterEntity> pe=repository.findByClave("visits.number");
		if(pe.isPresent()) {
			return pe.get().getValor();
		}
		return "";
	}
}
