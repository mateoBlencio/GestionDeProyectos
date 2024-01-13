package com.gestionProyectos.GestionDeProyectos.application.controllers;

import com.gestionProyectos.GestionDeProyectos.application.ResponseHandler;
import com.gestionProyectos.GestionDeProyectos.application.responses.RegistroCambiosResponse;
import com.gestionProyectos.GestionDeProyectos.services.RegistroCambiosServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registrosCambiosEstados")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RegistroCambiosController {
    final RegistroCambiosServiceImpl registroCambiosService;

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        try{
            val registrosCambios =  registroCambiosService.findAll(page, size)
                    .stream()
                    .map(RegistroCambiosResponse :: from);
            return ResponseHandler.success(registrosCambios);
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    // Endpoint para acceder al registro de cambios por proyecto.
    @GetMapping("/{nroProyecto}")
    public ResponseEntity<Object> findForProyect(@PathVariable Integer nroProyecto,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size){
        try{
            val registrosCambios = registroCambiosService.findForProyect(nroProyecto, page, size)
                    .stream()
                    .map(RegistroCambiosResponse::from);
            return ResponseHandler.success(registrosCambios);
        }catch (Exception e){
            return ResponseHandler.internalError();
        }
    }
}
