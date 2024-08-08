package com.nhat.structurizebe.controllers;

import com.nhat.structurizebe.models.dto.request.LoginRequest;
import com.nhat.structurizebe.models.dto.request.RegisterRequest;
import com.nhat.structurizebe.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request.getEmail(), request.getUsername(), request.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword()));
        } catch (UsernameNotFoundException e) {
            System.out.println("Invalid email or password");
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }
}