package com.icodeap.ecommerce.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    
    //@NotEmpty(message = "El nombre es obligatorio")
    private String username;
    
    @NotEmpty(message = "El nombre es obligatorio")
    private String firstName;
    
    private String lastName;
    
    @Email(message = "Formato de correo erroneo")
    @NotEmpty(message = "El email es obligatorio")
    private String email;
    
    private String address;
    
    @NotEmpty(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos")
    private String cellphone;
    
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;
    
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @NotEmpty(message = "La contraseña es obligatoria")
    private String password2;
    
    private UserType userType;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;


}
