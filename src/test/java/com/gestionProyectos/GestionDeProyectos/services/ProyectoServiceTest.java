package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.*;
import com.gestionProyectos.GestionDeProyectos.repositories.ClienteRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoXProyectoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.EstadoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.ProyectoRepository;
import org.hibernate.DuplicateMappingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProyectoServiceTest {
    @InjectMocks
    private ProyectoServiceImpl proyectoService;

    @Mock
    private ProyectoRepository proyectoRepository;
    @Mock
    private EstadoRepository estadoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private RegistroCambiosServiceImpl registroCambiosService;
    @Mock
    private EmpleadoXProyectoRepository empleadoXProyectoRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        proyectoService = new ProyectoServiceImpl(proyectoRepository, estadoRepository, clienteRepository,
                registroCambiosService, empleadoXProyectoRepository);
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

        assertEquals(proyecto1, resultado);
    }

    @Test
    void deleteTest() {
        Cliente cliente1 = new Cliente("JUAN", 12345678, "juan@gmail.com");
        cliente1.setNroCliente(1);
        Estado estado1 = new Estado("Terminado", "Proyecto Terminado");
        estado1.setNroEstado(1);
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

        // Configurar el mock para devolver el proyecto1 cuando se busque por el id 1
        when(proyectoRepository.findById(1)).thenReturn(Optional.of(proyecto1));

        // Configurar el mock para indicar que el proyecto con nroProyecto 1 existe
        when(proyectoRepository.existsById(1)).thenReturn(true);

        // Llamar al método delete con nroProyecto 1
        proyectoService.delete(1);

        // Verificar que se llamó al método deleteById con nroProyecto 1
        verify(proyectoRepository, times(1)).delete(proyecto1);
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
        proyectoNuevo.setPrecioProyecto(20000); // Se modifica el precio
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

        assertEquals(resultado.getNroProyecto(), 1);
        assertEquals(resultado.getFechaInicio(), proyectoNuevo.getFechaInicio());
        assertEquals(resultado.getPrecioProyecto(), proyectoNuevo.getPrecioProyecto());;
    }

    @Test
    void createTest(){
        Cliente cliente1 = new Cliente("JUAN", 12345678, "juan@gmail.com");
        cliente1.setNroCliente(1);
        Estado estado1 = new Estado("Pendiente","Tarea por hacer");
        estado1.setNroEstado(1);
        LocalDateTime fechaI1 =  LocalDateTime.of(2022,8,20,0,0, 0);
        LocalDateTime fechaFin1 =  LocalDateTime.of(2025,9,20,0,0, 0);

        Proyecto proyectoEsperado = new Proyecto();
        proyectoEsperado.setNroProyecto(1);
        proyectoEsperado.setFechaInicio(fechaI1);
        proyectoEsperado.setFechaLimite(fechaFin1);
        proyectoEsperado.setPrecioProyecto(15000);
        proyectoEsperado.setCliente(cliente1);
        proyectoEsperado.setNombreProyecto("Apple proyect");

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente1));
        when(estadoRepository.findById(1)).thenReturn(Optional.of(estado1));
        when(proyectoRepository.save(any())).thenReturn(proyectoEsperado);

        Proyecto proyectoResultado = proyectoService.create("Apple proyect",fechaFin1,15000,1);

        assertNotNull(proyectoResultado);
        assertEquals("Apple proyect", proyectoResultado.getNombreProyecto());
        assertEquals(15000, proyectoResultado.getPrecioProyecto());
        assertEquals(cliente1, proyectoEsperado.getCliente());
    }
}