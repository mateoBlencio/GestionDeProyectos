package com.gestionProyectos.GestionDeProyectos.services;

import com.gestionProyectos.GestionDeProyectos.model.Estado;
import com.gestionProyectos.GestionDeProyectos.repositories.EstadoRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EstadoServiceImpl {
    final EstadoRepository estadoRepository;

    public Page<Estado> findAll(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        return estadoRepository.findAll(pageable);
    }

    public Optional<Estado> findOne(Integer id){return estadoRepository.findById(id);}

    @Transactional
    public void update(final Integer id, final String nombreEstadoP, final String descripcionP){

        val estado = estadoRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Estado not found"));

        estado.update(nombreEstadoP, descripcionP);

        estadoRepository.save(estado);
    }

    @Transactional
    public Estado create(final String nombreEstadoP, final String descripcionP){
        val estado = new Estado(nombreEstadoP, descripcionP);
        return estadoRepository.save(estado);
    }
}
