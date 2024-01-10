package com.gestionProyectos.GestionDeProyectos.auth;

import com.gestionProyectos.GestionDeProyectos.application.requests.CreateEmpleadoRequest;
import com.gestionProyectos.GestionDeProyectos.auth.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request){
        try{
            return ResponseEntity.ok(authService.login(request));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody CreateEmpleadoRequest request){
        try{
            return ResponseEntity.ok(authService.register(request));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
