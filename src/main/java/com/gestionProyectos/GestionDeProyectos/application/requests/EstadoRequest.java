package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EstadoRequest {
    @NotBlank(message = "Nombre is mandatory")
    String nombreEstado;
    @NotBlank(message = "Descripcion is mandatory")
    String descripcion;
}
