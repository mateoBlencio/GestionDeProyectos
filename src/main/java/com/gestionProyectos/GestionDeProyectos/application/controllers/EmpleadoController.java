package com.gestionProyectos.GestionDeProyectos.application.controllers;

import com.gestionProyectos.GestionDeProyectos.application.ResponseHandler;
import com.gestionProyectos.GestionDeProyectos.application.requests.CreateEmpleadoRequest;
import com.gestionProyectos.GestionDeProyectos.application.requests.UpdateEmpleadoRequest;
import com.gestionProyectos.GestionDeProyectos.application.responses.EmpleadoResponse;
import com.gestionProyectos.GestionDeProyectos.services.EmpleadoServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleados")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EmpleadoController {
    final EmpleadoServiceImpl empleadoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('Gerente', 'Analista')")
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        try {
            val empleados = empleadoService.findAll(page, size)
                    .stream()
                    .map(EmpleadoResponse::from);
            return ResponseHandler.success(empleados);
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @GetMapping("/{nroEmpleado}")
    @PreAuthorize("hasAnyRole('Gerente', 'Analista')")
    public ResponseEntity<Object> findOne(@PathVariable Integer nroEmpleado){
        try{
            val empleado = empleadoService.findOne(nroEmpleado)
                    .orElseThrow(() -> new IllegalArgumentException("Empleado not found"));
            return ResponseHandler.success(EmpleadoResponse.from(empleado));
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @DeleteMapping("/{nroEmpleado}")
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> delete(@PathVariable Integer nroEmpleado){
        try{
            empleadoService.delete(nroEmpleado);
            return ResponseHandler.success();
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> create(@RequestBody CreateEmpleadoRequest createEmpleadoRequest){
        try{
            val empleado = empleadoService.create(createEmpleadoRequest.getNombre(),
                    createEmpleadoRequest.getNroRol(),
                    createEmpleadoRequest.getMail(),
                    createEmpleadoRequest.getNombreUsuario(),
                    createEmpleadoRequest.getPassword());
            return ResponseHandler.created(EmpleadoResponse.from(empleado));
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PatchMapping("/{nroEmpleado}")
    @PreAuthorize("hasAnyRole('Gerente','Analista')")
    public ResponseEntity<Object> update(@PathVariable Integer nroEmpleado,
                                         @RequestBody UpdateEmpleadoRequest updateEmpleadoRequest){
        try{
            empleadoService.update(nroEmpleado,
                    updateEmpleadoRequest.getNombre(),
                    updateEmpleadoRequest.getNroRolEmpleado(),
                    updateEmpleadoRequest.getMail(),
                    updateEmpleadoRequest.getNombreUsuario(),
                    updateEmpleadoRequest.getPassword());
            return ResponseHandler.success();
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    // SACAR
//    @GetMapping("/{nroEmpleado}")
//    public ResponseEntity<Object> findProyectsXEmpleados(@PathVariable Integer nroEmpleado){
//        try {
//            val proyectos = empleadoService.findProyectXEmpleado(nroEmpleado)
//                    .stream()
//                    .map(ProyectoResponse::from);
//            return ResponseHandler.success(proyectos);
//        } catch (Exception e){
//            return ResponseHandler.internalError();
//        }
//    }
}
