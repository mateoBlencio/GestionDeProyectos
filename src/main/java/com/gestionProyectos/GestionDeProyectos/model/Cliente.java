package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = Cliente.TABLE_NAME)
public class Cliente {
    public static final String TABLE_NAME = "cliente";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_cliente")
    Integer nroCliente;

    @Column(name = "nombre_cliente")
    String nombreCliente;

    @Column(name = "telefono")
    Integer telefono;

    @Column(name = "mail")
    String mail;

    public Cliente(){super();}

    public Cliente(String nombreClienteP, Integer telefonoP, String mailP){
        super();
        nombreCliente = nombreClienteP;
        telefono = telefonoP;
        mail = mailP;
    }

    public void update(Integer telefonoP, String mailP){
        telefono = telefonoP;
        mail = mailP;
    }
}
