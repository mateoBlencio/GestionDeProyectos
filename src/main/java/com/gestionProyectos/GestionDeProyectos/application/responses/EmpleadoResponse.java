package com.gestionProyectos.GestionDeProyectos.application.responses;

import com.gestionProyectos.GestionDeProyectos.model.Empleado;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class EmpleadoResponse {
    Integer nroEmpleado;
    String nombreEmpleado;
    RolResponse nroRolEmpleado;
    String mail;
    String nombreUsuario;
    Double sueldo;

    public static EmpleadoResponse from(Empleado aEmpleado){
        return EmpleadoResponse.builder()
                .nroEmpleado(aEmpleado.getNroEmpleado())
                .nombreEmpleado(aEmpleado.getNombreEmpleado())
                .nroRolEmpleado(RolResponse.from(aEmpleado.getRolEmpleado()))
                .sueldo(aEmpleado.calcularSueldo())
                .mail(aEmpleado.getMail())
                .nombreUsuario(aEmpleado.getNombreUsuario())
                .build();
    }
}
