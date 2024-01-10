package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.RolEmpleado;
import com.gestionProyectos.GestionDeProyectos.repositories.RolEmpleadoRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolServiceImpl {
    final RolEmpleadoRepository rolEmpleadoRepository;

    public Page<RolEmpleado> findAll(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return rolEmpleadoRepository.findAll(pageable);
    }

    public Optional<RolEmpleado> findOne(Integer nroRol){return rolEmpleadoRepository.findById(nroRol);}

    @Transactional
    public RolEmpleado create(String nombreRol,
                              Double precioXHora){
        val rol = new RolEmpleado(nombreRol, precioXHora);
        return rolEmpleadoRepository.save(rol);
    }

    @Transactional
    public void update(Integer nroRol,
                       String nombreRolP,
                       Double precioXHoraP){
        val rol = rolEmpleadoRepository.findById(nroRol).orElseThrow(()-> new IllegalArgumentException("Rol not found"));

        String nombreRol = (nombreRolP != null) ? nombreRolP : rol.getNombreRol();
        Double precioXHora = (precioXHoraP != null) ? precioXHoraP : rol.getPrecioXHora();

        rol.update(nombreRol, precioXHora);
    }

    @Transactional
    public void delete(Integer nroRol){
        try{
            rolEmpleadoRepository.deleteById(nroRol);
        }catch (Exception e){
            throw new IllegalArgumentException();
        }
    }
}
