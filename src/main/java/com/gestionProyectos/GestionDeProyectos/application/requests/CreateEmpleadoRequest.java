package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateEmpleadoRequest {
    @NotBlank(message = "Nombre is mandatory")
    String nombre;
    @NotBlank(message = "Rol is mandatory")
    Integer nroRol;
    @NotBlank(message = "Mail is mandatory")
    String mail;
    @NotBlank(message = "Nombre de usuario is mandatory")
    String nombreUsuario;
    @NotBlank(message = "Contraseña is mandatory")
    String password;
}
