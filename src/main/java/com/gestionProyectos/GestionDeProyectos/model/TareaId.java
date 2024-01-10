package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class TareaId implements Serializable {

    @Column(name = "nro_tarea")
    Integer nroTarea; // ver el tema de q las tareas empiecen en 1 para cada proyecto

    @ManyToOne
    @JoinColumn(name = "nro_proyecto")
    Proyecto nroProyecto;

    public TareaId(){super();}

    public TareaId(Integer nroTareaP, Proyecto nroProyectoP){
        super();
        nroTarea = nroTareaP;
        nroProyecto = nroProyectoP;
    }
}
