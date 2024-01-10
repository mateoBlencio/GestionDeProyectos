package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEmpleadoRequest {
    @NotBlank(message = "Nombre is mandatory")
    String nombre;
    @NotNull(message = "Rol is mandatory")
    Integer nroRolEmpleado;
    @NotBlank(message = "Mail is mandatory")
    String mail;
    @NotBlank(message = "Nombre de usuario is mandatory")
    String nombreUsuario;
    @NotBlank(message = "Contraseña is mandatory")
    String password;
}
