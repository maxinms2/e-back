package com.icodeap.ecommerce.backend.infrastructure.adapter;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.icodeap.ecommerce.backend.infrastructure.entity.ParameterEntity;

public interface ParameterRepository extends CrudRepository<ParameterEntity,Integer> {
    Optional<ParameterEntity> findByClave(String clave);

}
