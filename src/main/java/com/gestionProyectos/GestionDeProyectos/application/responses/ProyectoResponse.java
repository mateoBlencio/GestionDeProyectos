package com.gestionProyectos.GestionDeProyectos.application.responses;

import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProyectoResponse {
    Integer nroProyecto;
    String nombreProyecto;
    EstadoResponse estado;
    LocalDateTime fechaInicio;
    LocalDateTime fechaLimite;
    LocalDateTime fechaReentrega;
    Integer precioProyecto;
    ClienteResponse cliente;

    public static ProyectoResponse from(Proyecto aProyecto){
        return ProyectoResponse.builder()
                .nroProyecto(aProyecto.getNroProyecto())
                .nombreProyecto(aProyecto.getNombreProyecto())
                .estado(EstadoResponse.from(aProyecto.getEstado()))
                .fechaInicio(aProyecto.getFechaInicio())
                .fechaLimite(aProyecto.getFechaLimite())
                .fechaReentrega(aProyecto.getFechaReentrega())
                .precioProyecto(aProyecto.getPrecioProyecto())
                .cliente(ClienteResponse.from(aProyecto.getCliente()))
                .build();
    }
}
