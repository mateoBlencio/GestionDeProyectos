package com.gestionProyectos.GestionDeProyectos.application.controllers;

import com.gestionProyectos.GestionDeProyectos.application.ResponseHandler;
import com.gestionProyectos.GestionDeProyectos.application.requests.CreateClienteRequest;
import com.gestionProyectos.GestionDeProyectos.application.requests.UpdateClienteRequest;
import com.gestionProyectos.GestionDeProyectos.application.responses.ClienteResponse;
import com.gestionProyectos.GestionDeProyectos.services.ClienteServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ClienteController {
    final ClienteServiceImpl clienteService;

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size){
        try{
            val clientes = clienteService.findAll(page, size).stream().map(ClienteResponse::from);
            return ResponseHandler.success(clientes);
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @GetMapping("/{nroCliente}")
    public ResponseEntity<Object> findOne(@PathVariable Integer nroCliente){
        try{
            val cliente = clienteService.findOne(nroCliente).map(ClienteResponse::from);
            return ResponseHandler.success(cliente);
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> create(@RequestBody CreateClienteRequest createClienteRequest){
        try{
            val cliente = clienteService.create(createClienteRequest.getNombreCliente(),
                    createClienteRequest.getTelefono(), createClienteRequest.getMail());
            return ResponseHandler.created(ClienteResponse.from(cliente));
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }

    @PatchMapping("/{nroCliente}")
    @PreAuthorize("hasRole('Gerente')")
    public ResponseEntity<Object> update(@PathVariable Integer nroCliente,
                                         @RequestBody UpdateClienteRequest updateClienteRequest){
        try{
            clienteService.update(nroCliente,
                    updateClienteRequest.getTelefono(),
                    updateClienteRequest.getMail());
            return ResponseHandler.success();
        } catch (Exception e){
            return ResponseHandler.internalError();
        }
    }
}
