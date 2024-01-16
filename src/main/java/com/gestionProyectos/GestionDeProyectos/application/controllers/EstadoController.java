package com.gestionProyectos.GestionDeProyectos.application.controllers;

import com.gestionProyectos.GestionDeProyectos.application.ResponseHandler;
import com.gestionProyectos.GestionDeProyectos.application.requests.EstadoRequest;
import com.gestionProyectos.GestionDeProyectos.services.EstadoServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estados")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EstadoController {
    final EstadoServiceImpl estadoService;

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        try{
            val estados = estadoService.findAll(page, size);
            return ResponseHandler.success(estados.getContent());
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> create(@RequestBody EstadoRequest createEstadoRequest){
        try{
            val estado = estadoService.create(createEstadoRequest.getNombreEstado(),
                    createEstadoRequest.getDescripcion());
            return ResponseHandler.created(estado);
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PatchMapping("/{nroEstado}")
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> update(@PathVariable Integer nroEstado,
                                         @RequestBody EstadoRequest estadoRequest){
        try{
            estadoService.update(nroEstado,
                    estadoRequest.getNombreEstado(),
                    estadoRequest.getDescripcion());
            return ResponseHandler.success();
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }
}
