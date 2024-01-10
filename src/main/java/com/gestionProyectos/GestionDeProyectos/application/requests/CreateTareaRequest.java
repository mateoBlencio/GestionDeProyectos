package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTareaRequest {
    @NotNull(message = "")
    Integer nroProyecto;
    @NotBlank(message = "")
    String nombreTarea;
    @NotBlank(message = "")
    Integer prioridad;
    @NotNull(message = "")
    Integer empleado;
}