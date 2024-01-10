package com.gestionProyectos.GestionDeProyectos.auth;

import com.gestionProyectos.GestionDeProyectos.application.requests.CreateEmpleadoRequest;
import com.gestionProyectos.GestionDeProyectos.auth.request.LoginRequest;
import com.gestionProyectos.GestionDeProyectos.auth.response.AuthResponse;
import com.gestionProyectos.GestionDeProyectos.auth.jwt.JwtService;
import com.gestionProyectos.GestionDeProyectos.model.Empleado;
import com.gestionProyectos.GestionDeProyectos.repositories.EmpleadoRepository;
import com.gestionProyectos.GestionDeProyectos.services.EmpleadoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final EmpleadoServiceImpl empleadoService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmpleadoRepository empleadoRepository;

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNombreUsuario(), request.getPassword()));
        UserDetails user = empleadoRepository.findEmpleadoByNombreUsuario(request.getNombreUsuario()).orElseThrow(() -> new IllegalArgumentException("Problema buscando al usuario"));
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(CreateEmpleadoRequest request){
        Empleado empleado = empleadoService.create(request.getNombre(),
                request.getNroRol(),
                request.getMail(),
                request.getNombreUsuario(),
                request.getPassword());
        return AuthResponse.builder()
                .token(jwtService.getToken(empleado))
                .build();
    }
}
