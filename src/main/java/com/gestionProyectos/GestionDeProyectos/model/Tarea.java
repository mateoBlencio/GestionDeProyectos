package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = Tarea.TABLE_NAME)
public class Tarea {
    public static final String TABLE_NAME = "tarea";

    @EmbeddedId
    TareaId tareaId;

    @Column(name = "nombre")
    String nombreTarea;

    @Column(name = "prioridad")
    Integer prioridad; // del 1 al 5, mientras mayor num mayor es la prioridad

    @Column(name = "fecha_creacion")
    LocalDateTime fechaCreacion;

    @Column(name = "fecha_finalizacion")
    LocalDateTime fechaFinalizacion;

    @ManyToOne
    @JoinColumn(name = "encargado")
    Empleado empleadoEncargado;

    @ManyToOne
    @JoinColumn(name = "estado")
    Estado estadoTarea;

    public Tarea(){super();}

    public Tarea(Integer nroTareaP, Proyecto nroProyectoP, String nombreTareaP, Integer prioridadP,
                 LocalDateTime fechaCreacionP, Empleado empleadoEncargadoP,
                 Estado estadoTareaP){
        super();
        tareaId = new TareaId(nroTareaP, nroProyectoP);
        nombreTarea = nombreTareaP;
        prioridad = prioridadP;
        fechaCreacion = fechaCreacionP;
        fechaFinalizacion = null;
        empleadoEncargado = empleadoEncargadoP;
        estadoTarea = estadoTareaP;
    }

    public void update(String nombreTareaP, Integer prioridadP,
                       Empleado empleadoEncargadoP, LocalDateTime fechaFinalizacionP,
                       Estado estadoTareaP){
        nombreTarea = nombreTareaP;
        prioridad = prioridadP;
        fechaFinalizacion = fechaFinalizacionP;
        empleadoEncargado = empleadoEncargadoP;
        estadoTarea = estadoTareaP;
    }
}
