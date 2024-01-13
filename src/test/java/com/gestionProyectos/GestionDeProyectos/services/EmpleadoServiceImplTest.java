package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.Empleado;
import com.gestionProyectos.GestionDeProyectos.model.RolEmpleado;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoXProyectoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.RolEmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private RolEmpleadoRepository rolEmpleadoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmpleadoXProyectoRepository empleadoXProyectoRepository;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        empleadoService = new EmpleadoServiceImpl( empleadoRepository, rolEmpleadoRepository, empleadoXProyectoRepository, passwordEncoder);
    }

    @Test
    void testCreateEmpleado() {
        // Arrange
        RolEmpleado rolEmpleado = new RolEmpleado("Desarrollador", 25.0);
        when(rolEmpleadoRepository.findById(anyInt())).thenReturn(Optional.of(rolEmpleado));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        Empleado esperado = new Empleado("John Doe",  rolEmpleado, "john@example.com",
                "john", "password");
        when(empleadoRepository.save(any())).thenReturn(esperado);

        // Act
        Empleado result = empleadoService.create("John Doe",
                1, "john@example.com",
                "john", "password");

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getNombreEmpleado());
        assertEquals(rolEmpleado, result.getRolEmpleado());
        assertEquals("john@example.com", result.getMail());
        assertEquals("john", result.getNombreUsuario());
    }

    @Test
    void testUpdateEmpleado() {
        Empleado authenticatedEmpleado = new Empleado("John Doe", new RolEmpleado("Gerente", 40.0), "john@example.com", "john", "encodedPassword");
        setAuthenticatedEmpleado(authenticatedEmpleado);

        // Arrange
        RolEmpleado rol = new RolEmpleado("Analista", 35.0);
        Empleado existingEmpleado = new Empleado("John Doe", rol, "john@example.com", "john", "encodedPassword");
        when(rolEmpleadoRepository.findById(anyInt())).thenReturn(Optional.of(rol));
        when(empleadoRepository.findById(anyInt())).thenReturn(Optional.of(existingEmpleado));
        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");

        // Act
        empleadoService.update(1, "Updated Name",
                5, "new.email@example.com", null, "newPassword");

        // Assert
        assertEquals("Updated Name", existingEmpleado.getNombreEmpleado());
        assertEquals("new.email@example.com", existingEmpleado.getMail());
        assertEquals("john", existingEmpleado.getNombreUsuario());
        assertEquals("newEncodedPassword", existingEmpleado.getPassword());
    }

    @Test
    void testDeleteEmpleado() {
        // Authentication
        RolEmpleado rol = new RolEmpleado("Gerente", 40.0);
        Empleado authenticatedEmpleado = new Empleado("John Doe", rol, "john@example.com",
                "john", "encodedPassword");
        authenticatedEmpleado.setNroEmpleado(1);
        setAuthenticatedEmpleado(authenticatedEmpleado);

        // Arrange
        when(empleadoRepository.findById(anyInt())).thenReturn(Optional.of(authenticatedEmpleado));
        when(empleadoRepository.existsById(1)).thenReturn(true);

        // Act
        empleadoService.delete(1);

        // Assert
        verify(empleadoRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteEmpleadoAccessDenied() {
        // Arrange
        Empleado authenticatedEmpleado = new Empleado("John Doe", new RolEmpleado("Desarrollador",	25.0), "john@example.com", "john", "encodedPassword");
        authenticatedEmpleado.setNroEmpleado(1);
        setAuthenticatedEmpleado(authenticatedEmpleado);
        when(empleadoRepository.existsById(anyInt())).thenReturn(true);

        // Act/Assert
        assertThrows(AccessDeniedException.class, () -> empleadoService.delete(2));
    }

    private void setAuthenticatedEmpleado(Empleado empleado) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(empleado);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}

