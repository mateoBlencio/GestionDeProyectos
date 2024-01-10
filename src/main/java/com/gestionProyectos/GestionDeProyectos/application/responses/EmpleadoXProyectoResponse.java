package com.gestionProyectos.GestionDeProyectos.application.responses;

import com.gestionProyectos.GestionDeProyectos.model.EmpleadoXProyecto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmpleadoXProyectoResponse {
    EmpleadoResponse nroEmpleado;
    ProyectoResponse nroProyecto;
    EmpleadoResponse nroLider;

    public static EmpleadoXProyectoResponse from(EmpleadoXProyecto aEmpleadoXProyecto){
        return EmpleadoXProyectoResponse.builder()
                .nroEmpleado(EmpleadoResponse.from(aEmpleadoXProyecto.getEmpleadoXProyectoId().getEmpleado()))
                .nroProyecto(ProyectoResponse.from(aEmpleadoXProyecto.getEmpleadoXProyectoId().getProyecto()))
                .nroLider(EmpleadoResponse.from(aEmpleadoXProyecto.getLider()))
                .build();
    }
}
