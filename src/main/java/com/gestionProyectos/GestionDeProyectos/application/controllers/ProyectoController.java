package com.gestionProyectos.GestionDeProyectos.application.controllers;

import com.gestionProyectos.GestionDeProyectos.application.ResponseHandler;
import com.gestionProyectos.GestionDeProyectos.application.requests.CreateProyectoRequest;
import com.gestionProyectos.GestionDeProyectos.application.requests.UpdateProyectoRequest;
import com.gestionProyectos.GestionDeProyectos.application.responses.ProyectoResponse;
import com.gestionProyectos.GestionDeProyectos.services.ProyectoServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proyectos")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProyectoController {
    final ProyectoServiceImpl proyectoService;
    // Los roles pueden ser: 'Desarrollador', 'Diseñador', 'Gerente', 'Tester', 'Analista'

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        try{
            val proyects = proyectoService.findAll(page, size)
                    .stream()
                    .map(ProyectoResponse::from)
                    .toList();
            return ResponseHandler.success(proyects);
        }catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable Integer id){
        try{
            val proyect = proyectoService.findOne(id);
            return ResponseHandler.success(proyect);
        }catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Gerente', 'Analista')")
    public ResponseEntity<Object> create(@RequestBody CreateProyectoRequest createProyectoRequest){
        try{
            val proyecto = proyectoService.create(createProyectoRequest.getNombreProyecto(),
                    createProyectoRequest.getFechaLimite(),
                    createProyectoRequest.getPrecio(),
                    createProyectoRequest.getCliente());
            return ResponseHandler.created(ProyectoResponse.from(proyecto));
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id){
        try{
            proyectoService.delete(id);
            return ResponseHandler.success();
        }catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('Gerente')")
    public ResponseEntity<Object> update(@PathVariable Integer id,
                                         @RequestBody UpdateProyectoRequest proyectoRequest){
        try{
            proyectoService.update(id,
                    proyectoRequest.getEstado(),
                    proyectoRequest.getFechaLimite(),
                    proyectoRequest.getFechaReentrega(),
                    proyectoRequest.getPrecioProyecto(),
                    proyectoRequest.getCliente());
            return ResponseHandler.noContent();
        } catch (IllegalArgumentException e) {
            return ResponseHandler.badRequest(e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.internalError();
        }
    }

    @PatchMapping("/cambiarEstado/{nroProyecto}")
    @PreAuthorize("hasAnyRole('Desarrollador', 'Diseñador', 'Gerente', 'Tester', 'Analista')")
    public ResponseEntity<Object> cambiarEstadoProyecto(@PathVariable Integer nroProyecto,
                                                        @RequestParam Integer nroEstado){
        try{
            proyectoService.cambiarEstado(nroProyecto, nroEstado);
            return ResponseHandler.success();
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }
}
