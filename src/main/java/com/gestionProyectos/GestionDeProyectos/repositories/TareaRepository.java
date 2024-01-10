package com.gestionProyectos.GestionDeProyectos.repositories;

import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
import com.gestionProyectos.GestionDeProyectos.model.Tarea;
import com.gestionProyectos.GestionDeProyectos.model.TareaId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, TareaId> {

    Page<Tarea> findTareaByTareaId_NroProyecto(Proyecto tareaId_nroProyecto, PageRequest pageable);

    List<Tarea> findTareaByTareaId_NroProyecto(Proyecto tareaId_nroProyecto);
}
