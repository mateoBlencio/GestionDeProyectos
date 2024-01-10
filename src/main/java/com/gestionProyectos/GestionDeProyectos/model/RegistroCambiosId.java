package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistroCambiosId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "nro_proyecto")
    Proyecto proyecto;

    @ManyToOne
    @JoinColumn(name = "nro_estado")
    Estado estado;

    @Column(name = "fecha_cambio")
    LocalDateTime fechaCambio;

    public RegistroCambiosId(){super();}

    public RegistroCambiosId(Proyecto proyectoP, Estado estadoP, LocalDateTime fechaCambioP){
        super();
        proyecto = proyectoP;
        estado = estadoP;
        fechaCambio = fechaCambioP;
    }

    public void update(Proyecto proyectoP, Estado estadoP, LocalDateTime fechaCambioP){
        proyecto = proyectoP;
        estado = estadoP;
        fechaCambio = fechaCambioP;
    }
}
