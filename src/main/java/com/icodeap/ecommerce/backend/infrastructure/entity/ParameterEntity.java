package com.icodeap.ecommerce.backend.infrastructure.entity;

import java.time.LocalDateTime;

import com.icodeap.ecommerce.backend.domain.model.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parameters")
@Data
@NoArgsConstructor
public class ParameterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String clave;
    private String valor;
}
