package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = EmpleadoXProyecto.TABLE_NAME)
public class EmpleadoXProyecto {
    public static final String TABLE_NAME = "empleado_x_proyecto";

    @EmbeddedId
    EmpleadoXProyectoId empleadoXProyectoId;

    @ManyToOne
    @JoinColumn(name = "lider_proyecto")
    Empleado lider;

    public EmpleadoXProyecto(){super();}

    public EmpleadoXProyecto(Empleado empleadoP, Proyecto proyectoP, Empleado liderP){
        super();
        empleadoXProyectoId = new EmpleadoXProyectoId(empleadoP, proyectoP);
        lider = liderP;
    }

    public void update(Empleado empleadoP, Proyecto proyectoP, Empleado liderP){
        empleadoXProyectoId = new EmpleadoXProyectoId(empleadoP, proyectoP);
        lider = liderP;
    }
}
