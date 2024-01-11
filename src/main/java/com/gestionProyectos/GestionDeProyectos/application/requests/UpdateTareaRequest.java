package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTareaRequest {
    @NotBlank(message = "Nombre is mandatory")
    String nombre;
    @NotBlank(message = "Prioridad is mandatory")
    Integer prioridad;
    @NotNull(message = "Empleado is mandatory")
    Integer empleado;
    @NotNull(message = "Estado is mandatory")
    Integer nroEstado;
}
