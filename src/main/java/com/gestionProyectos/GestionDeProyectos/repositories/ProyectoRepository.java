package com.gestionProyectos.GestionDeProyectos.repositories;

import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
}
