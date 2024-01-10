package com.gestionProyectos.GestionDeProyectos.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    @NotBlank(message = "nombre de usuario is mandatory")
    String nombreUsuario;
    @NotBlank(message = "password is mandatory")
    String password;
}
