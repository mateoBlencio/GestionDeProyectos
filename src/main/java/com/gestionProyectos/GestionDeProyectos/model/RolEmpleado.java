package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = RolEmpleado.TABLE_NAME)
public class RolEmpleado {
    public static final String TABLE_NAME = "rol_empleado";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_rol")
    Integer numeroRol;

    @Column(name = "nombre_rol")
    String nombreRol;

    @Column(name = "precio_por_hora")
    Double precioXHora;

    public RolEmpleado(){super();}

    public RolEmpleado(String nombreRolP, Double precioXHoraP){
        super();
        nombreRol = nombreRolP;
        precioXHora = precioXHoraP;
    }

    public void update(String nombreRolP, Double precioXHoraP){
        nombreRol = nombreRolP;
        precioXHora = precioXHoraP;
    }
}
