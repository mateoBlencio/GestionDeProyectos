package com.gestionProyectos.GestionDeProyectos.application.responses;

import com.gestionProyectos.GestionDeProyectos.model.RegistroCambios;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistroCambiosResponse {
    Integer nroProyecto;
    Integer nroEstadoViejo;
    String nombreEstadoviejo;
    Integer nroEstadoNuevo;
    String nombreEstadoNuevo;
    LocalDateTime fechaCambio;

    public static RegistroCambiosResponse from(RegistroCambios aRegistro){
        return RegistroCambiosResponse.builder()
                .nroProyecto(aRegistro.getRegistroCambiosId().getProyecto().getNroProyecto())
                .nroEstadoViejo(aRegistro.getRegistroCambiosId().getEstado().getNroEstado())
                .nombreEstadoviejo(aRegistro.getRegistroCambiosId().getEstado().getNombreEstado())
                .nroEstadoNuevo(aRegistro.getEstadoNuevo().getNroEstado())
                .nombreEstadoNuevo(aRegistro.getEstadoNuevo().getNombreEstado())
                .fechaCambio(aRegistro.getRegistroCambiosId().getFechaCambio())
                .build();
    }
}
