package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.Empleado;
import com.gestionProyectos.GestionDeProyectos.model.EmpleadoXProyecto;
import com.gestionProyectos.GestionDeProyectos.model.EmpleadoXProyectoId;
import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoXProyectoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.ProyectoRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EmpleadoXProyectoServiceImpl {
    final EmpleadoXProyectoRepository empleadoXProyectoRepository;
    final EmpleadoRepository empleadoRepository;
    final ProyectoRepository proyectoRepostiry;

    public Page<EmpleadoXProyecto> findAll(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return empleadoXProyectoRepository.findAll(pageable);
    }

    public Optional<EmpleadoXProyecto> findOne(Integer nroEmpleado, Integer nroProyecto){

        val empleado = empleadoRepository.findById(nroEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("Empleado not found"));

        val proyecto = proyectoRepostiry.findById(nroProyecto)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto not found"));

        EmpleadoXProyectoId id = new EmpleadoXProyectoId(empleado, proyecto);

        return empleadoXProyectoRepository.findById(id);
    }

    @Transactional
    public EmpleadoXProyecto create(Integer nroEmpleado, Integer nroProyecto,
                                    Integer nroLider){
        val empleado = empleadoRepository.findById(nroEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("Empleado not found"));

        val proyecto = proyectoRepostiry.findById(nroProyecto)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto not found"));

        val lider = empleadoRepository.findById(nroLider)
                .orElseThrow(() -> new IllegalArgumentException("Lider not found"));

        if ( verificarCumpleReglasGrupos(proyecto, empleado, lider)
                && verificarCumpleReglasGruposxEmpleado(empleado) ){
            EmpleadoXProyecto empleadoXProyecto = new EmpleadoXProyecto(empleado, proyecto, lider);
            return empleadoXProyectoRepository.save(empleadoXProyecto);
        } else {
            throw new IllegalArgumentException("No se cumplieron reglas de grupo");
        }
    }

    // Cada proyecto puede tener como maximo 6 integrantes con el lider incluido
    private boolean verificarCumpleReglasGrupos(Proyecto proyecto, Empleado empleadoNuevo,Empleado lider){
        List<EmpleadoXProyecto> listaEmpleados = empleadoXProyectoRepository
                .findEmpleadoXProyectoByEmpleadoXProyectoId_Proyecto(proyecto);

        // Si todavia no hay ningun empleado ni lider asignado al proyecto devuelve true
        if (listaEmpleados.isEmpty()){
            return true;
        }

        // Si el tamaño de la lista es de 5 quiere decir que ya existen 5 empleados asignados mas un lider
        if (listaEmpleados.size() == 5){
            return false;
        }

        // Verificacion de que el empleado nuevo no se este asignando 2 veces
        for (EmpleadoXProyecto empleadoXproyecto : listaEmpleados){
            if ( (!Objects.equals(empleadoNuevo, empleadoXproyecto.getEmpleadoXProyectoId().getEmpleado()))
                    && (Objects.equals(lider, empleadoXproyecto.getLider()))
                    && (!Objects.equals(lider, empleadoXproyecto.getEmpleadoXProyectoId().getEmpleado())) ){
                return true;
            }
        }
        return false;
    }

    // Verifica que nungun empleado este en mas de 2 proyectos en simultaneo
    private boolean verificarCumpleReglasGruposxEmpleado(Empleado empleadoNuevo){
//        List<EmpleadoXProyecto> listaProyectosEmpleadoSimple = empleadoXProyectoRepository
//                .findEmpleadoXProyectoByEmpleadoXProyectoId_Empleado(empleadoNuevo);

        List<EmpleadoXProyecto> listaProyectos = empleadoXProyectoRepository
                .findEmpleadoXProyectoByEmpleadoXProyectoId_EmpleadoOrLider(empleadoNuevo, empleadoNuevo);

//        if (listaProyectosEmpleadoSimple.isEmpty()){
//            return true;
//        } else if (listaProyectosEmpleadoSimple.size() == 2) {
//            return false;
//        }

        if ( listaProyectos.isEmpty() ) {
            return true;
        } else return listaProyectos.size() != 2;
    }

    @Transactional
    public void delete(Integer nroEmpleado, Integer nroProyecto){
        val empleado = empleadoRepository.findById(nroEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("Empleado not found"));

        val proyecto = proyectoRepostiry.findById(nroProyecto)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto not found"));

        EmpleadoXProyectoId id = new EmpleadoXProyectoId(empleado, proyecto);
        empleadoXProyectoRepository.deleteById(id);
    }

    public List<Proyecto> findProyectXEmpleado(Integer nroEmpleado){
        val empleado = empleadoRepository.findById(nroEmpleado)
                .orElseThrow(()-> new IllegalArgumentException("Empleado not found"));
        val empleadoXProyectos = empleadoXProyectoRepository.findEmpleadoXProyectoByEmpleadoXProyectoId_Empleado(empleado);

        List<Proyecto> proyectos = new ArrayList<>();
        empleadoXProyectos.forEach(empleadoXProyecto -> {
            proyectos.add(empleadoXProyecto.getEmpleadoXProyectoId().getProyecto());
        });

        return proyectos;
    }
}
