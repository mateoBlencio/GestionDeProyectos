package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmpleadoXProyectoId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "nro_empleado")
    Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "nro_proyecto")
    Proyecto proyecto;

    public EmpleadoXProyectoId(){super();}

    public EmpleadoXProyectoId(Empleado empleadoP, Proyecto proyectoP){
        super();
        empleado = empleadoP;
        proyecto = proyectoP;
    }

    public void update(Empleado empleadoP, Proyecto proyectoP){
        empleado = empleadoP;
        proyecto = proyectoP;
    }
}
