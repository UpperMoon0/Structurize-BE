package org.nstut.luvit.controller;

import org.nstut.luvit.dto.LoginRequest;
import org.nstut.luvit.dto.LoginResponse;
import org.nstut.luvit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setStatus(200);
            loginResponse.setToken("dummy_token");
            System.out.println("Login successful");
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException e) {
            System.out.println("Email: " + loginRequest.getEmail() + " Password: " + loginRequest.getPassword());
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setStatus(401);
            loginResponse.setToken(null);
            System.out.println("Login failed with message: " + e.getMessage());
            return ResponseEntity.status(401).body(loginResponse);
        }
    }
}