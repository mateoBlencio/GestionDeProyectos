package com.gestionProyectos.GestionDeProyectos.repositories;

import com.gestionProyectos.GestionDeProyectos.model.Empleado;
import com.gestionProyectos.GestionDeProyectos.model.EmpleadoXProyecto;
import com.gestionProyectos.GestionDeProyectos.model.EmpleadoXProyectoId;
import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoXProyectoRepository extends JpaRepository<EmpleadoXProyecto, EmpleadoXProyectoId> {
    List<EmpleadoXProyecto> findEmpleadoXProyectoByEmpleadoXProyectoId_Empleado(Empleado empleado);

    List<EmpleadoXProyecto> findEmpleadoXProyectoByEmpleadoXProyectoId_Proyecto(Proyecto proyecto);

    List<EmpleadoXProyecto> findEmpleadoXProyectoByEmpleadoXProyectoId_EmpleadoOrLider(Empleado empleado, Empleado lider);

    void deleteAllByEmpleadoXProyectoId_Proyecto(Proyecto proyecto);
}
