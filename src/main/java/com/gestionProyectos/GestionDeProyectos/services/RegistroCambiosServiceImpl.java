package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.Estado;
import com.gestionProyectos.GestionDeProyectos.model.Proyecto;
import com.gestionProyectos.GestionDeProyectos.model.RegistroCambios;
import com.gestionProyectos.GestionDeProyectos.repositories.ProyectoRepository;
import com.gestionProyectos.GestionDeProyectos.repositories.RegistroCambiosRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RegistroCambiosServiceImpl {
    final RegistroCambiosRepository registroCambiosRepository;
    final ProyectoRepository proyectoService;

    @Transactional
    public void create(Proyecto proyectoP, final Estado estadoViejo, final Estado estadoNuevo){
        LocalDateTime hoy = LocalDateTime.now();

        RegistroCambios registroCambios = new RegistroCambios(proyectoP, estadoViejo, estadoNuevo, hoy);

        registroCambiosRepository.save(registroCambios);
    }

    public Page<RegistroCambios> findAll(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return registroCambiosRepository.findAll(pageable);
    }

    public Page<RegistroCambios> findForProyect(Integer nroProyecto, int page, int size){
        PageRequest pageable = PageRequest.of(page, size);

        Proyecto proyecto = proyectoService.findById(nroProyecto)
                .orElseThrow(()-> new IllegalArgumentException("Proyecto not found"));

        return registroCambiosRepository.findByRegistroCambiosId_Proyecto(proyecto, pageable);
    }
}
