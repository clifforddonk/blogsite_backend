package com.blog.controllers;

import com.blog.models.User;
import com.blog.payload.LoginRequest;
import com.blog.payload.AuthResponse;
import com.blog.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            boolean userExists = authService.checkUserExists(user.getEmail()); // Check if user already exists
            if (userExists) {
                return ResponseEntity.badRequest().body("User already exists. Please use a different Email.");
            }

            authService.register(user);
            return ResponseEntity.ok().body("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(new AuthResponse(token, "Login successful!"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials. Please check your username and password.");
        }
    }
}
