package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.*;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoXProyectoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.TareaRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TareaServiceImpl {
    final TareaRepository tareaRepository;
    final ProyectoServiceImpl proyectoService;
    final EmpleadoServiceImpl empleadoService;
    final EstadoServiceImpl estadoService;
    final EmpleadoXProyectoRepository empleadoXProyectoRepository;

    public Page<Tarea> findAll(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return tareaRepository.findAll(pageable);
    }

    public Page<Tarea> findForProyect(Integer nroProyecto,
                                      int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        val proyecto = proyectoService.findOne(nroProyecto)
                .orElseThrow(()-> new IllegalArgumentException("Proyecto not found"));
        return tareaRepository.findTareaByTareaId_NroProyecto(proyecto, pageable);
    }

    public Optional<Tarea> findOne(Integer nroTarea, Integer nroProyecto){
        val proyecto = proyectoService.findOne(nroProyecto)
                .orElseThrow(()-> new IllegalArgumentException("Proyecto not found"));
        TareaId id = new TareaId(nroTarea, proyecto);
        return tareaRepository.findById(id);
    }

    @Transactional
    public void delete(Integer nroProyecto, Integer nroTarea){
        try{
            val proyecto = proyectoService.findOne(nroProyecto)
                    .orElseThrow(() -> new IllegalArgumentException("Proyecto not found"));
            TareaId id = new TareaId(nroTarea, proyecto);
            tareaRepository.deleteById(id);
        }catch (Exception e){
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public Tarea create(Integer nroProyectoP,
                        String nombreTareaP,
                        Integer prioridadP,
                        Integer empleadoEncargadoP) {

        LocalDateTime fechaCreacionP = LocalDateTime.now();

        val proyecto = proyectoService.findOne(nroProyectoP)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto not found"));

        val empleado = empleadoService.findOne(empleadoEncargadoP)
                .orElseThrow(() -> new IllegalArgumentException("Empleado not found"));

        // La tarea se inicializa en "Pendiente"
        val estado = estadoService.findOne(1)
                .orElseThrow(() -> new IllegalArgumentException("Estado not found"));

        List<Tarea> tareas = tareaRepository.findTareaByTareaId_NroProyecto(proyecto);
        Integer nroTareaP = null;
        if (tareas.isEmpty()){
            nroTareaP = 1;
        }else {
            nroTareaP  = tareas.size() + 1;
        }

        if( 1 <= prioridadP && prioridadP <= 5 && esEmpleadoDeProyecto(empleado, proyecto) && proyecto.estaVigente()) {
            Tarea tarea = new Tarea(nroTareaP, proyecto, nombreTareaP, prioridadP, fechaCreacionP, empleado, estado);
            return tareaRepository.save(tarea);
        } else {
            throw new IllegalArgumentException("No se pudo crear");
        }
    }

    private boolean esEmpleadoDeProyecto(Empleado empleado, Proyecto proyecto){
        val empleadoXProyecto = empleadoXProyectoRepository.findEmpleadoXProyectoByEmpleadoXProyectoId_EmpleadoAndEmpleadoXProyectoId_Proyecto(empleado, proyecto)
                .orElseThrow(()-> new IllegalArgumentException("El empleado no esta asignado al proyecto de la tarea requerida"));
        return empleadoXProyecto != null;
    }

    @Transactional
    public void update(Integer nroProyecto,
                        Integer nroTarea,
                        String nombreTareaP,
                       Integer prioridadP,
                       Integer empleadoEncargadoP,
                       Integer nroEstado){

        val proyecto = proyectoService.findOne(nroProyecto)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto not found"));

        TareaId id = new TareaId(nroTarea, proyecto);

        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea not found"));

        Empleado empleado = null;
        if(empleadoEncargadoP != null) {
            empleado = empleadoService.findOne(empleadoEncargadoP)
                    .orElseThrow(() -> new IllegalArgumentException("Empleado not found"));
        } else {
            empleado = tarea.getEmpleadoEncargado();
        }

        LocalDateTime fechaFinalizacion = null;

        Estado estadoTarea = null;
        if ( nroEstado != null && nroEstado != 1 ) {

            estadoTarea = estadoService.findOne(nroEstado)
                     .orElseThrow(()-> new IllegalArgumentException("Estado not found"));
             if ( estadoTarea.getNroEstado() == 3 ){ fechaFinalizacion = LocalDateTime.now(); }

        } else {
                estadoTarea = tarea.getEstadoTarea();
        }

        Integer prioridad = (prioridadP != null) ? prioridadP : tarea.getPrioridad();
        String nombreTarea = (nombreTareaP != null) ? nombreTareaP : tarea.getNombreTarea();

        if (esEmpleadoDeProyecto(empleado, proyecto)) {
            tarea.update(nombreTarea, prioridad, empleado, fechaFinalizacion, estadoTarea);
            tareaRepository.save(tarea);
        } else {
            throw new IllegalArgumentException("No se pudo modificar tarea");
        }
    }
}
