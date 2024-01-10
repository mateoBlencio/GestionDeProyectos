package com.gestionProyectos.GestionDeProyectos.application.responses;

import com.gestionProyectos.GestionDeProyectos.model.RolEmpleado;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RolResponse {
    Integer nroRol;
    String nombreRol;
    Double precioXHora;

    public static RolResponse from(RolEmpleado aRol){
        return RolResponse.builder()
                .nroRol(aRol.getNumeroRol())
                .nombreRol(aRol.getNombreRol())
                .precioXHora(aRol.getPrecioXHora())
                .build();
    }
}
