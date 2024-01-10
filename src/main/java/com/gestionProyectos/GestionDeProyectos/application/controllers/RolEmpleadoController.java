package com.gestionProyectos.GestionDeProyectos.application.controllers;

import com.gestionProyectos.GestionDeProyectos.application.ResponseHandler;
import com.gestionProyectos.GestionDeProyectos.application.requests.RolEmpleadoRequest;
import com.gestionProyectos.GestionDeProyectos.application.responses.RolResponse;
import com.gestionProyectos.GestionDeProyectos.services.RolServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolEmpleadoController {
    final RolServiceImpl rolService;

    @GetMapping
    @PreAuthorize("hasAnyRole('Gerente', 'Analista')")
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        try{
            val roles = rolService.findAll(page, size)
                    .stream()
                    .map(RolResponse::from);
            return ResponseHandler.success(roles);
        }catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> create(@RequestBody RolEmpleadoRequest rolRequest){
        try{
            val rol = rolService.create(rolRequest.getNombreRol(), rolRequest.getPrecioXHora());
            return ResponseHandler.created(RolResponse.from(rol));
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @DeleteMapping("/{nroRol}")
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> delete(@PathVariable Integer nroRol){
        try{
            rolService.delete(nroRol);
            return ResponseHandler.success();
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PatchMapping("/{nroRol}")
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> update(@PathVariable Integer nroRol,
                                         @RequestBody RolEmpleadoRequest rolRequest){
        try {
            rolService.update(nroRol,
                    rolRequest.getNombreRol(),
                    rolRequest.getPrecioXHora());
            return ResponseHandler.success();
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }
}
