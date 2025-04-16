package com.example.demo.repo;

import java.util.UUID;

public class LoginResponse {
    private UUID id;
    private String email;
    private String role;

    public LoginResponse(UUID id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
