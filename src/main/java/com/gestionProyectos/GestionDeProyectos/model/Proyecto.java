package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = Proyecto.TABLE_NAME)
public class Proyecto {
    public static final String TABLE_NAME = "proyecto";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_proyecto")
    Integer nroProyecto;

    @Column(name = "nombre_proyecto")
    String nombreProyecto;

    @ManyToOne
    @JoinColumn(name = "estado")
    Estado estado;

    @Column(name = "fecha_inicio")
    LocalDateTime fechaInicio;

    @Column(name = "fecha_limite")
    LocalDateTime fechaLimite;

    @Column(name = "fecha_reentrega")
    LocalDateTime fechaReentrega;

    @Column(name = "precio_proyecto")
    Integer precioProyecto;

    @ManyToOne
    @JoinColumn(name = "nro_cliente")
    Cliente cliente;

    public Proyecto(){super();}

    public Proyecto(String nombreProyectoP, Estado estadoP, LocalDateTime fechaInicioP, LocalDateTime fechaLimiteP, Integer precioProyectoP, Cliente clienteP){
        super();
        nombreProyecto = nombreProyectoP;
        estado = estadoP;
        fechaInicio = fechaInicioP;
        fechaLimite = fechaLimiteP;
        fechaReentrega = null; // "null" ya que no se puede crear un proyecto y asignarle inmediatamente una fecha de reentrega
        precioProyecto = precioProyectoP;
        cliente = clienteP;
    }

    public void update(Estado estadoP, LocalDateTime fechaLimiteP, LocalDateTime fechaReentregaP,
                    Integer precioProyectoP,Cliente clienteP){
        estado = estadoP;
        fechaLimite = fechaLimiteP;
        fechaReentrega = fechaReentregaP;
        precioProyecto = precioProyectoP;
        cliente = clienteP;
    }

    public void cambiarEstado(Estado estadoNuevo){
        estado = estadoNuevo;
    }

    public boolean estaVigente(){
        estado = this.getEstado();
        return estado.getNroEstado() != 3;
    }
}
