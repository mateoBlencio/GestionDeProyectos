package com.gestionProyectos.GestionDeProyectos.repositories;

import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
import com.gestionProyectos.GestionDeProyectos.model.RegistroCambios;
import com.gestionProyectos.GestionDeProyectos.model.RegistroCambiosId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroCambiosRepository extends JpaRepository<RegistroCambios, RegistroCambiosId> {
    Page<RegistroCambios> findByRegistroCambiosId_Proyecto(Proyecto proyecto, PageRequest pageable);
}
