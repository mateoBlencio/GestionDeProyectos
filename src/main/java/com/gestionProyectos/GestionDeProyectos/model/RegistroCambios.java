package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = RegistroCambios.TABLE_NAME)
public class RegistroCambios {
    public static final String TABLE_NAME = "registro_cambios_estado";

    @EmbeddedId
    RegistroCambiosId registroCambiosId;

    @ManyToOne
    @JoinColumn(name = "nro_estado_nuevo")
    Estado estadoNuevo;

    public RegistroCambios(){super();}

    public RegistroCambios(Proyecto proyectoP, Estado estadoP, Estado estadoNuevoP, LocalDateTime fechaCambioP){
        super();
        registroCambiosId = new RegistroCambiosId(proyectoP, estadoP, fechaCambioP);
        estadoNuevo = estadoNuevoP;
    }

    public void update(Proyecto proyectoP, Estado estadoP, LocalDateTime fechaCambioP, Estado estadoNuevoP){
        registroCambiosId = new RegistroCambiosId(proyectoP, estadoP, fechaCambioP);
        estadoNuevo = estadoNuevoP;
    }
}
