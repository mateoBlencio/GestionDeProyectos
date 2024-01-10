package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolEmpleadoRequest {
    @NotBlank(message = "Nombre is mandatory")
    String nombreRol;
    @NotBlank(message = "Precio por hora is mandatory")
    Double precioXHora;
}
