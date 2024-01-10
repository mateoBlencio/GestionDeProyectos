package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProyectoRequest {
    @NotBlank(message = "Nombre de proyecto is mandatory")
    String nombreProyecto;
    @NotBlank(message = "Fecha limite is mandatory")
    LocalDateTime fechaLimite;
    @NotBlank(message = "Precio proyecto is mandatory")
    Integer precio;
    @NotNull(message = "Cliente is mandatory")
    Integer cliente;
}
