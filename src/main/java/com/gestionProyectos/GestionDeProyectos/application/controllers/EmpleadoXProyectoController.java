package com.gestionProyectos.GestionDeProyectos.application.controllers;

import com.gestionProyectos.GestionDeProyectos.application.ResponseHandler;
import com.gestionProyectos.GestionDeProyectos.application.requests.CreateEmpleadoXProyectoRequest;
import com.gestionProyectos.GestionDeProyectos.application.responses.EmpleadoXProyectoResponse;
import com.gestionProyectos.GestionDeProyectos.services.EmpleadoXProyectoServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleados/proyectos")
@RequiredArgsConstructor
public class EmpleadoXProyectoController {
    final EmpleadoXProyectoServiceImpl empleadoXProyectoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('Gerente', 'Analista')")
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        try{
            val empleadosXProyectos = empleadoXProyectoService.findAll(page, size)
                    .stream()
                    .map(EmpleadoXProyectoResponse::from);
            return ResponseHandler.success(empleadosXProyectos);
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @GetMapping("/empleadosAndProyectos")
    @PreAuthorize("hasAnyRole('Gerente', 'Analista')")
    public ResponseEntity<Object> findOne(@RequestParam Integer nroEmpleado,
                                          @RequestParam Integer nroProyecto){
        try{
            val empleadosXProyectos = empleadoXProyectoService.findOne(nroEmpleado, nroProyecto)
                    .orElseThrow(() -> new IllegalArgumentException("Object not found"));
            return ResponseHandler.success(EmpleadoXProyectoResponse.from(empleadosXProyectos));
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    // Asignar proyecto a empleado
    @PostMapping
    @PreAuthorize("hasAnyRole('Gerente', 'Analista')")
    public ResponseEntity<Object> create(@RequestBody CreateEmpleadoXProyectoRequest createEmpleadoXProyectRequest){
        try{
            val empleadoXProyecto = empleadoXProyectoService.create(createEmpleadoXProyectRequest.getNroEmpleado(),
                    createEmpleadoXProyectRequest.getNroProyecto(), createEmpleadoXProyectRequest.getNroLider());
            return ResponseHandler.created(EmpleadoXProyectoResponse.from(empleadoXProyecto));
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseHandler.internalError();
        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> delete(@RequestParam Integer nroEmpleado,
                                         @RequestParam Integer nroProyecto){
        try{
            empleadoXProyectoService.delete(nroEmpleado, nroProyecto);
            return ResponseHandler.success();
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }
}
