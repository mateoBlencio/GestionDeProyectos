package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.Cliente;
import com.gestionProyectos.GestionDeProyectos.model.Estado;
import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
import com.gestionProyectos.GestionDeProyectos.repositories.EstadoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RequiredArgsConstructor
class ProyectoControllerTest {
    @InjectMocks
    private ProyectoServiceImpl proyectoService;
    @Mock
    private ProyectoRepository proyectoRepository;

    @Test
    void findAll() {
        Cliente cliente1 = new Cliente("JUAN", 12345678, "juan@gmail.com");
        Estado estado1 = new Estado("Terminado", "Proyecto Terminado");
        LocalDateTime fechaI1 =  LocalDateTime.of(2022,8,20,0,0, 0);
        LocalDateTime fechaFin1 =  LocalDateTime.of(2022,9,20,0,0, 0);

        Cliente cliente2 = new Cliente("MANUEL", 12345678, "manuel@gmail.com");
        Estado estado2 = new Estado("Terminado", "Proyecto Terminado");
        LocalDateTime fechaI2 =  LocalDateTime.of(2022,8,20,0,0, 0);
        LocalDateTime fechaFin2 =  LocalDateTime.of(2022,9,20,0,0, 0);

        Proyecto proyecto1 = new Proyecto();
        proyecto1.setNroProyecto(1);
        proyecto1.setEstado(estado1);
        proyecto1.setFechaInicio(fechaI1);
        proyecto1.setFechaLimite(fechaFin1);
        proyecto1.setPrecioProyecto(15000);
        proyecto1.setCliente(cliente1);
        proyecto1.setNombreProyecto("Apple proyect");

        Proyecto proyecto2 = new Proyecto();
        proyecto2.setNroProyecto(2);
        proyecto2.setEstado(estado2);
        proyecto2.setFechaInicio(fechaI2);
        proyecto2.setFechaLimite(fechaFin2);
        proyecto2.setPrecioProyecto(15000);
        proyecto2.setCliente(cliente2);
        proyecto2.setNombreProyecto("Motorola proyect");

        List<Proyecto> proyectList = new ArrayList<>();
        proyectList.add(proyecto1);
        proyectList.add(proyecto2);

        when(proyectoRepository.findAll()).thenReturn(proyectList);

        Page<Proyecto> resultado = proyectoService.findAll(0,10);

        Assertions.assertEquals(proyectList.size(), resultado.getTotalPages()*10);
    }

    @Test
    void findOne() {
        Cliente cliente1 = new Cliente("JUAN", 12345678, "juan@gmail.com");
        Estado estado1 = new Estado("Terminado", "Proyecto Terminado");
        LocalDateTime fechaI1 =  LocalDateTime.of(2022,8,20,0,0, 0);
        LocalDateTime fechaFin1 =  LocalDateTime.of(2022,9,20,0,0, 0);

        Proyecto proyecto1 = new Proyecto();
        proyecto1.setNroProyecto(1);
        proyecto1.setEstado(estado1);
        proyecto1.setFechaInicio(fechaI1);
        proyecto1.setFechaLimite(fechaFin1);
        proyecto1.setPrecioProyecto(15000);
        proyecto1.setCliente(cliente1);
        proyecto1.setNombreProyecto("Apple proyect");

        when(proyectoRepository.findById(1)).thenReturn(Optional.of(proyecto1));

        Optional<Proyecto> resultadoOptional = proyectoService.findOne(1);

        Proyecto resultado = resultadoOptional.get();

        Assertions.assertEquals(proyecto1, resultado);
    }

    @Test
    void delete() {
        Cliente cliente1 = new Cliente("JUAN", 12345678, "juan@gmail.com");
        Estado estado1 = new Estado("Terminado", "Proyecto Terminado");
        LocalDateTime fechaI1 =  LocalDateTime.of(2022,8,20,0,0, 0);
        LocalDateTime fechaFin1 =  LocalDateTime.of(2022,9,20,0,0, 0);

        Proyecto proyecto1 = new Proyecto();
        proyecto1.setNroProyecto(1);
        proyecto1.setEstado(estado1);
        proyecto1.setFechaInicio(fechaI1);
        proyecto1.setFechaLimite(fechaFin1);
        proyecto1.setPrecioProyecto(15000);
        proyecto1.setCliente(cliente1);
        proyecto1.setNombreProyecto("Apple proyect");

        doNothing().when(proyectoRepository).deleteById(proyecto1.getNroProyecto());

        when(proyectoRepository.findById(1)).thenReturn(Optional.of(proyecto1));

        proyectoService.delete(proyecto1.getNroProyecto());

        try{
            proyectoService.findOne(proyecto1.getNroProyecto());
        } catch (Exception e){
            Assertions.assertTrue(e instanceof ChangeSetPersister.NotFoundException);
        }

    }

    @Test
    void update() {
        Cliente cliente1 = new Cliente("JUAN", 12345678, "juan@gmail.com");
        Estado estado1 = new Estado("Terminado", "Proyecto Terminado");
        LocalDateTime fechaI1 =  LocalDateTime.of(2022,8,20,0,0, 0);
        LocalDateTime fechaFin1 =  LocalDateTime.of(2022,9,20,0,0, 0);

        Proyecto proyecto1 = new Proyecto();
        proyecto1.setNroProyecto(1);
        proyecto1.setEstado(estado1);
        proyecto1.setFechaInicio(fechaI1);
        proyecto1.setFechaLimite(fechaFin1);
        proyecto1.setPrecioProyecto(15000);
        proyecto1.setCliente(cliente1);
        proyecto1.setNombreProyecto("Apple proyect");

        Optional<Proyecto> proyectoOptional = Optional.of(proyecto1);

        when(proyectoRepository.findById(1)).thenReturn(proyectoOptional);

        Proyecto proyectoNuevo = new Proyecto();
        proyectoNuevo.setNroProyecto(1);
        proyectoNuevo.setEstado(estado1);
        proyectoNuevo.setFechaInicio(fechaI1);
        proyectoNuevo.setFechaLimite(fechaFin1);
        proyectoNuevo.setPrecioProyecto(20000);
        proyectoNuevo.setCliente(cliente1);
        proyectoNuevo.setNombreProyecto("Apple proyect");

        proyectoService.update(proyectoNuevo.getNroProyecto(),
                proyectoNuevo.getEstado().getNroEstado(),
                proyectoNuevo.getFechaInicio(),
                proyectoNuevo.getFechaLimite(),
                proyectoNuevo.getPrecioProyecto(),
                proyectoNuevo.getCliente().getNroCliente());

        Optional<Proyecto> resultadoOptional = proyectoService.findOne(proyectoNuevo.getNroProyecto());

        Proyecto resultado = resultadoOptional.get();

        Assertions.assertEquals(resultado.getNroProyecto(), 1);
    }
}