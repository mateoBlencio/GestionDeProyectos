package com.gestionProyectos.GestionDeProyectos.application.controllers;

import com.gestionProyectos.GestionDeProyectos.application.ResponseHandler;
import com.gestionProyectos.GestionDeProyectos.application.requests.CreateProyectoRequest;
import com.gestionProyectos.GestionDeProyectos.application.requests.CreateTareaRequest;
import com.gestionProyectos.GestionDeProyectos.application.requests.UpdateTareaRequest;
import com.gestionProyectos.GestionDeProyectos.application.responses.TareaResponse;
import com.gestionProyectos.GestionDeProyectos.services.TareaServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tareas")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TareaController {
    final TareaServiceImpl tareaService;

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        try{
            val tareas = tareaService.findAll(page, size)
                    .stream()
                    .map(TareaResponse::from);
            return ResponseHandler.success(tareas);
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @GetMapping("/{nroProyecto}")
    public ResponseEntity<Object> findForProyect(@PathVariable Integer nroProyecto,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size){
        try{
            val tarea = tareaService.findForProyect(nroProyecto, page, size)
                    .stream()
                    .map(TareaResponse::from);
            return ResponseHandler.success(tarea);
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @GetMapping("/tarea")
    public ResponseEntity<Object> findOne(@RequestParam Integer nroTarea, @RequestParam Integer nroProyecto){
        try{
            val tarea = tareaService.findOne(nroTarea, nroProyecto)
                    .orElseThrow(()-> new IllegalArgumentException("Tarea not found"));
            return ResponseHandler.success(TareaResponse.from(tarea));
        } catch (Exception e) {
            return ResponseHandler.internalError();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Analista', 'Desarrollador', 'Tester', 'Gerente')")
    public ResponseEntity<Object> create(@RequestBody CreateTareaRequest createTareaRequest){
        try{
            val tarea = tareaService.create(createTareaRequest.getNroProyecto(),
                    createTareaRequest.getNombreTarea(),
                    createTareaRequest.getPrioridad(),
                    createTareaRequest.getEmpleado());
            return ResponseHandler.created(TareaResponse.from(tarea));
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseHandler.internalError();
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('Analista', 'Gerente')")
    public ResponseEntity<Object> delete(@RequestParam Integer nroTarea,
                                         @RequestParam Integer nroProyecto){
        try{
            tareaService.delete(nroProyecto, nroTarea);
            return ResponseHandler.success();
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PatchMapping
    @PreAuthorize("hasAnyRole('Desarrollador', 'Analista', 'Gerente')")
    public  ResponseEntity<Object> update(@RequestParam Integer nroTarea,
                                          @RequestParam Integer nroProyecto,
                                          @RequestBody UpdateTareaRequest updateTareaRequest){
        try{
            tareaService.update(nroProyecto, nroTarea,
                    updateTareaRequest.getNombre(),
                    updateTareaRequest.getPrioridad(),
                    updateTareaRequest.getEmpleado(),
                    updateTareaRequest.getNroEstado());
            return ResponseHandler.success();
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }
}
