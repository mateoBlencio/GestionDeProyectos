package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.Empleado;
import com.gestionProyectos.GestionDeProyectos.model.Estado;
import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
import com.gestionProyectos.GestionDeProyectos.model.Tarea;
import com.gestionProyectos.GestionDeProyectos.model.TareaId;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoXProyectoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.EstadoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.ProyectoRepository;
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
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TareaServiceImpl {
    final TareaRepository tareaRepository;
    final ProyectoRepository proyectoRepository;
    final EmpleadoRepository empleadoRepository;
    final EstadoRepository estadoRepository;
    final EmpleadoXProyectoRepository empleadoXProyectoRepository;

    public Page<Tarea> findAll(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return tareaRepository.findAll(pageable);
    }

    public Page<Tarea> findForProyect(Integer nroProyecto,
                                      int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        val proyecto = proyectoRepository.findById(nroProyecto)
                .orElseThrow(()-> new IllegalArgumentException("Proyecto not found"));
        return tareaRepository.findTareaByTareaId_NroProyecto(proyecto, pageable);
    }

    public Optional<Tarea> findOne(Integer nroTarea, Integer nroProyecto){
        val proyecto = proyectoRepository.findById(nroProyecto)
                .orElseThrow(()-> new IllegalArgumentException("Proyecto not found"));
        TareaId id = new TareaId(nroTarea, proyecto);
        return tareaRepository.findById(id);
    }

    @Transactional
    public void delete(Integer nroProyecto, Integer nroTarea){
        try{
            val proyecto = proyectoRepository.findById(nroProyecto)
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

        val proyecto = proyectoRepository.findById(nroProyectoP)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto not found"));

        val empleado = empleadoRepository.findById(empleadoEncargadoP)
                .orElseThrow(() -> new IllegalArgumentException("Empleado not found"));

        // La tarea se inicializa en "Pendiente"
        val estado = estadoRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("Estado not found"));

        List<Tarea> tareas = tareaRepository.findTareaByTareaId_NroProyecto(proyecto);
        Integer nroTareaP;
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

        val proyecto = proyectoRepository.findById(nroProyecto)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto not found"));

        TareaId id = new TareaId(nroTarea, proyecto);

        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea not found"));

        Empleado empleado;
        if(empleadoEncargadoP != null) {
            empleado = empleadoRepository.findById(empleadoEncargadoP)
                    .orElseThrow(() -> new IllegalArgumentException("Empleado not found"));
        } else {
            empleado = tarea.getEmpleadoEncargado();
        }

        LocalDateTime fechaFinalizacion = null;

        Estado estadoTarea;
        if ( nroEstado != null && nroEstado != 1 ) {

            estadoTarea = estadoRepository.findById(nroEstado)
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
