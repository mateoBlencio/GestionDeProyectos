package com.gestionProyectos.GestionDeProyectos.repositories;

import com.gestionProyectos.GestionDeProyectos.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
}
