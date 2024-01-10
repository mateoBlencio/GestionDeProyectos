package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = Estado.TABLE_NAME)
public class Estado {
    public static final String TABLE_NAME = "estado";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_estado")
    Integer nroEstado;

    @Column(name = "nombre_estado")
    String nombreEstado;

    @Column(name = "descripcion")
    String descripcion;

    public Estado(){super();}

    public Estado(String nombreEstadoP, String descripcionP){
        super();
        nombreEstado = nombreEstadoP;
        descripcion = descripcionP;
    }

    public void update(String nombreEstadoP, String descripcionP){
        nombreEstado = nombreEstadoP;
        descripcion = descripcionP;
    }
}
