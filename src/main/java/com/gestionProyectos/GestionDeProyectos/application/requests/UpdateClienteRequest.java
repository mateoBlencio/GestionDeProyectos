package com.gestionProyectos.GestionDeProyectos.application.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateClienteRequest {
    @NotBlank(message = "Telefono is mandatory")
    Integer telefono;
    @NotBlank(message = "Mail is mandatory")
    String mail;
}
