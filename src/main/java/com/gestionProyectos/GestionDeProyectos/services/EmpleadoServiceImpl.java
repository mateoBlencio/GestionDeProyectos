package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.Empleado;
import com.gestionProyectos.GestionDeProyectos.model.EmpleadoXProyecto;
import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
import com.gestionProyectos.GestionDeProyectos.model.RolEmpleado;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoXProyectoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.ProyectoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.RolEmpleadoRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EmpleadoServiceImpl {
    final EmpleadoRepository empleadoRepository;
    final RolEmpleadoRepository rolEmpleadoRepository;
    final EmpleadoXProyectoRepository empleadoXProyectoRepository;
    final PasswordEncoder passwordEncoder;


    // Obtener el empleado autenticado/actual.
    private Empleado getAuthenticatedEmpleado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Empleado) {
            return (Empleado) authentication.getPrincipal();
        }
        return null;
    }

    public Optional<Empleado> findOne(Integer id){return empleadoRepository.findById(id);}

    public Page<Empleado> findAll(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return empleadoRepository.findAll(pageable);}

    @Transactional
    public Empleado create(String nombreEmpleadoP,
                           Integer nroRolEmpleadoP,
                           String mailP,
                           String nombreUsuarioP,
                           String passwordP){
        val rolEmpleado = rolEmpleadoRepository.findById(nroRolEmpleadoP)
                .orElseThrow(()-> new IllegalArgumentException("Rol not found"));
        Empleado empleado = new Empleado(nombreEmpleadoP, rolEmpleado, mailP , nombreUsuarioP, passwordEncoder.encode(passwordP));
        return empleadoRepository.save(empleado);
    }

    @Transactional
    public void update(Integer nroEmpleado,
                        String nombreEmpleadoP,
                       Integer nroRolEmpleadoP,
                       String mailP,String nombreUsuarioP,
                       String passwordP){

        Empleado empleadoActual = getAuthenticatedEmpleado();

        if (empleadoActual == null) {
            // Manejar el caso cuando el empleado no está autenticado
            throw new AccessDeniedException("Empleado no autenticado");
        }

        List<String> rolesPermitidos = Arrays.asList("Gerente", "Analista");
        if ((Objects.equals(empleadoActual.getNroEmpleado(), nroEmpleado))
                || (rolesPermitidos.contains(empleadoActual.getRolEmpleado().getNombreRol()))) {

            Empleado empleado = empleadoRepository.findById(nroEmpleado)
                    .orElseThrow(() -> new IllegalArgumentException("Empleado not found"));

            RolEmpleado rolEmpleado = null;
            if (nroRolEmpleadoP != null) {
                rolEmpleado = rolEmpleadoRepository.findById(nroRolEmpleadoP)
                        .orElseThrow(() -> new IllegalArgumentException("Rol not found"));
            } else {
                rolEmpleado = empleado.getRolEmpleado();
            }

            String nombreEmpleado = (nombreEmpleadoP != null) ? nombreEmpleadoP : empleado.getNombreEmpleado();
            String mail = (mailP != null) ? mailP : empleado.getMail();
            String nombreUsuario = (nombreUsuarioP != null) ? nombreUsuarioP : empleado.getNombreUsuario();
            String password = (passwordP != null) ? passwordEncoder.encode(passwordP) : empleado.getPassword();

            empleado.update(nombreEmpleado, rolEmpleado, mail, nombreUsuario, password);
            empleadoRepository.save(empleado);
        }else {
            throw new AccessDeniedException("Acceso denegado");
        }
    }

    @Transactional
    public void delete(Integer nroEmpleado){
        Empleado empleadoActual = getAuthenticatedEmpleado();

        if (empleadoActual == null){
            throw  new AccessDeniedException("Empleado no autenticado");
        }

        List<String> rolesPermitidos = Arrays.asList("Gerente", "Analista");

        if ((empleadoActual.getNroEmpleado().equals(nroEmpleado))
                || (rolesPermitidos.contains(empleadoActual.getRolEmpleado().getNombreRol()))) {
            try {
                eliminarProyectosinvolucrados(nroEmpleado);
                empleadoRepository.deleteById(nroEmpleado);
            } catch (Exception e) {
                throw new IllegalArgumentException();
            }
        } else {
            throw new AccessDeniedException("Acceso denegado");
        }
    }
    @Transactional
    protected void eliminarProyectosinvolucrados(Integer nroEmpleado){
        try{
            val empleado = empleadoRepository.findById(nroEmpleado)
                    .orElseThrow(()-> new IllegalArgumentException("Empleado not found"));

            val empleadoXProyectos = empleadoXProyectoRepository
                    .findEmpleadoXProyectoByEmpleadoXProyectoId_Empleado(empleado);

            if(!empleadoXProyectos.isEmpty()){
                for (val empleadoXProyecto : empleadoXProyectos){
                    empleadoXProyectoRepository.deleteById(empleadoXProyecto.getEmpleadoXProyectoId());
                }
            }
        } catch (Exception e){
            throw new IllegalArgumentException("No se pudo eliminar");
        }
    }
}
