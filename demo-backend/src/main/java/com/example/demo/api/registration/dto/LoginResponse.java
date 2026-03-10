package com.example.demo.api.registration.dto;

public class LoginResponse {

    private String id;
    private String email;
    private String role;
    private String status;
    private String token;

    public LoginResponse(String id, String email, String role, String status, String token) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.status = status;
        this.token = token;
    }

    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getToken() { return token; }
}
