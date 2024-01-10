package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CreateClienteRequest {
    @NotBlank(message = "Nombre is mandatory")
    String nombreCliente;
    @NotBlank(message = "Telefono is mandatory")
    Integer telefono;
    @NotBlank(message = "Mail is mandatory")
    String mail;
}
