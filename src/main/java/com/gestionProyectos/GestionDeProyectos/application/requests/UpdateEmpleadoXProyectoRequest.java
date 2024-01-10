package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEmpleadoXProyectoRequest {
    @NotNull(message = "Empleado is mandatory")
    Integer nroEmpleado;
    @NotNull(message = "Proyecto is mandatory")
    Integer nroProyecto;
    @NotBlank(message = "Lider is mandatory")
    Integer nroLider;
}
