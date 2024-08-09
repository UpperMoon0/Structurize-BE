package com.nhat.structurizebe.controllers;

import com.nhat.structurizebe.exception.EmailAlreadyExistException;
import com.nhat.structurizebe.exception.RoleNotFoundException;
import com.nhat.structurizebe.exception.UsernameAlreadyExistException;
import com.nhat.structurizebe.models.dto.request.LoginRequest;
import com.nhat.structurizebe.models.dto.request.RegisterRequest;
import com.nhat.structurizebe.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request.getEmail(), request.getUsername(), request.getPassword());
        } catch (EmailAlreadyExistException e) {
            LOGGER.warning("Email already exists");
            return ResponseEntity.badRequest().body("Email already exists");
        } catch (UsernameAlreadyExistException e) {
            LOGGER.warning("Username already exists");
            return ResponseEntity.badRequest().body("Username already exists");
        }
        catch (RoleNotFoundException e) {
            LOGGER.severe("Role not found");
            return ResponseEntity.badRequest().body("Register failed, please try again later");
        }

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request.getEmail(), request.getPassword()));
        } catch (UsernameNotFoundException e) {
            LOGGER.warning("Invalid email or password");
            return ResponseEntity.badRequest().body("");
        }
    }
}