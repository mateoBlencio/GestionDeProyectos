package com.gestionProyectos.GestionDeProyectos.application.responses;

import com.gestionProyectos.GestionDeProyectos.model.Tarea;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TareaResponse {
    Integer nroTarea;
    ProyectoResponse nroProyecto;
    String nombre;
    EstadoResponse estado;
    LocalDateTime fechaCreacion;
    LocalDateTime fechaFinalizacion;
    EmpleadoResponse encargado;


    public static TareaResponse from(Tarea aTarea){
        return TareaResponse.builder()
                .nroTarea(aTarea.getTareaId().getNroTarea())
                .nroProyecto(ProyectoResponse.from(aTarea.getTareaId().getNroProyecto()))
                .nombre(aTarea.getNombreTarea())
                .estado(EstadoResponse.from(aTarea.getEstadoTarea()))
                .fechaCreacion(aTarea.getFechaCreacion())
                .fechaFinalizacion(aTarea.getFechaFinalizacion())
                .encargado(EmpleadoResponse.from(aTarea.getEmpleadoEncargado()))
                .build();
    }
}
