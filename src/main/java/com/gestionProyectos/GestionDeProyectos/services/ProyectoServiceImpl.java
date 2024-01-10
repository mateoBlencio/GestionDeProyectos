package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.Cliente;
import com.gestionProyectos.GestionDeProyectos.model.Estado;
import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
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

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProyectoServiceImpl {
    final ProyectoRepository proyectoRepository;
    final EstadoServiceImpl estadoService;
    final ClienteServiceImpl clienteService;
    final RegistroCambiosServiceImpl registroCambiosService;
    final EmpleadoXProyectoRepository empleadoXProyectoRepository;

    public Page<Proyecto> findAll(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return proyectoRepository.findAll(pageable);
    }

    public Optional<Proyecto> findOne(Integer id){return proyectoRepository.findById(id);}

    @Transactional
    public void delete(Integer nroProyecto){
        val proyecto = proyectoRepository.findById(nroProyecto)
                .orElseThrow(()-> new IllegalArgumentException("Proyect not found"));
        eliminarEmpleadosXProyectosInvolucrados(proyecto);
        proyectoRepository.delete(proyecto);
    }

    @Transactional
    protected void eliminarEmpleadosXProyectosInvolucrados(Proyecto proyecto){
        try{
            empleadoXProyectoRepository.deleteAllByEmpleadoXProyectoId_Proyecto(proyecto);
        } catch (Exception e){
            throw new IllegalArgumentException("No se pudo eliminar", e);
        }
    }

    @Transactional
    public void update( final Integer nroProyecto,  Integer nro_estado, LocalDateTime fechaLimiteP,
                        LocalDateTime fechaReentregaP,  Integer precioProyectoP,  Integer nro_cliente){

        val proyecto = proyectoRepository.findById(nroProyecto)
                .orElseThrow(()-> new IllegalArgumentException("Proyect not found"));

        Estado estado = null;
        if (nro_estado != null) {
            cambiarEstado(nroProyecto, nro_estado);
        }else {
            estado = proyecto.getEstado();
        }

        Cliente cliente = null;
        if (nro_cliente != null) {
            cliente = clienteService.findOne(nro_cliente)
                    .orElseThrow(()-> new IllegalArgumentException("Cliente not found"));
        }else {
            cliente = proyecto.getCliente();
        }

        LocalDateTime fechaLimite = (fechaLimiteP != null) ? fechaLimiteP : proyecto.getFechaLimite();
        LocalDateTime fechaReentrega = (fechaReentregaP != null) ? fechaReentregaP : proyecto.getFechaReentrega();
        Integer precioProyecto = (precioProyectoP != null) ? precioProyectoP : proyecto.getPrecioProyecto();

        // Revisar tema fechaReentrega, tambien debe pasar condiciones.
        proyecto.update(estado, fechaLimite, fechaReentrega, precioProyecto, cliente);

        proyectoRepository.save(proyecto);
    }

    @Transactional
    public Proyecto create(final String nombreProyectoP,
                           final LocalDateTime fechaLimiteP,
                           final Integer precioProyecto,
                           final Integer nro_cliente){

        // El proyecto inicia en estado "PENDIENTE"
        val estado = estadoService.findOne(1)
                .orElseThrow(()-> new IllegalArgumentException("Estado not found"));

        val cliente = clienteService.findOne(nro_cliente)
                .orElseThrow(()-> new IllegalArgumentException("Cliente not found"));

        LocalDateTime fechaInicio = LocalDateTime.now();

        val proyect = new Proyecto(nombreProyectoP, estado, fechaInicio, fechaLimiteP, precioProyecto, cliente);

        return proyectoRepository.save(proyect);
    }

    @Transactional
    public void cambiarEstado( Integer nroProyecto, Integer nroEstadoNuevo){

        val proyecto = proyectoRepository.findById(nroProyecto)
                .orElseThrow(()-> new IllegalArgumentException("Proyect not found"));

        val estadoViejo = proyecto.getEstado();

        val estadoNuevo = estadoService.findOne(nroEstadoNuevo)
                .orElseThrow(()-> new IllegalArgumentException("Estado not found"));

        if ( nroEstadoNuevo == 1 || Objects.equals(estadoViejo, estadoNuevo) ){
            return;
        }

        if (nroEstadoNuevo == 3){
            eliminarEmpleadosXProyectosInvolucrados(proyecto);
        }

        proyecto.cambiarEstado(estadoNuevo);
        registroCambiosService.create(proyecto, estadoViejo, estadoNuevo);
        proyectoRepository.save(proyecto);
    }
}
