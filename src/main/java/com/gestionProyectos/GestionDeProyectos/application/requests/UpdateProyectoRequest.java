package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateProyectoRequest {
    Integer estado;
    LocalDateTime fechaLimite;
    LocalDateTime fechaReentrega;
    Integer precioProyecto;
    Integer cliente;
}
