package com.gestionProyectos.GestionDeProyectos.application.responses;

import com.gestionProyectos.GestionDeProyectos.model.Estado;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EstadoResponse {
    Integer nroEstado;
    String nombreEstado;
    String descripcion;

    public static EstadoResponse from(Estado aEstado){
        return EstadoResponse.builder()
                .nroEstado(aEstado.getNroEstado())
                .nombreEstado(aEstado.getNombreEstado())
                .descripcion(aEstado.getDescripcion())
                .build();
    }
}
