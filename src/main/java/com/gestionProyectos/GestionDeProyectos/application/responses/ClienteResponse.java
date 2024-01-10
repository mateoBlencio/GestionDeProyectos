package com.gestionProyectos.GestionDeProyectos.application.responses;

import com.gestionProyectos.GestionDeProyectos.model.Cliente;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClienteResponse {
    Integer nroCliente;
    String nombreCliente;
    Integer telefono;
    String mail;

    public static ClienteResponse from (Cliente aCliente){
        return ClienteResponse.builder()
                .nroCliente(aCliente.getNroCliente())
                .nombreCliente(aCliente.getNombreCliente())
                .telefono(aCliente.getTelefono())
                .mail(aCliente.getMail())
                .build();
    }
}
