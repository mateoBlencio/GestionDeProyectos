package com.gestionProyectos.GestionDeProyectos.auth.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    String userName;
    String password;
    String nombreEmpleadoP;
    Integer nroRolEmpleadoP;
    String mailP;
}
