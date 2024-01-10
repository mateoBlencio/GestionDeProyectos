package com.gestionProyectos.GestionDeProyectos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = Empleado.TABLE_NAME)
public class Empleado implements UserDetails {
    public static final String TABLE_NAME = "empleado";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_empleado")
    Integer nroEmpleado;

    @Column(name = "nombre_empleado")
    String nombreEmpleado;

    @ManyToOne
    @JoinColumn(name="nro_rol_empleado")
    RolEmpleado rolEmpleado;

    @Column(name = "mail")
    String mail;

    @Column(name = "nombre_usuario", unique = true, nullable = false)
    String nombreUsuario;

    @Column(name = "password")
    String password;

    public Empleado(){super();}

    public Empleado(String nombreEmpleadoP, RolEmpleado rolEmpleadoP, String mailP, String nombreUsuarioP, String passwordP){
        super();
        nombreEmpleado = nombreEmpleadoP;
        rolEmpleado = rolEmpleadoP;
        mail = mailP;
        nombreUsuario = nombreUsuarioP;
        password = passwordP;
    }

    public void update(String nombreEmpleadoP, RolEmpleado rolEmpleadoP, String mailP,String nombreUsuarioP, String passwordP){
        nombreEmpleado = nombreEmpleadoP;
        rolEmpleado = rolEmpleadoP;
        mail = mailP;
        nombreUsuario = nombreUsuarioP;
        password = passwordP;
    }

    public Double calcularSueldo(){
        Double precioXhora = getRolEmpleado().getPrecioXHora();
        return (8 * precioXhora);
    }

    // Metodos de User Details
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((getRolEmpleado().getNombreRol())));
    }

    @Override
    public String getUsername() {
        return nombreUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
