package com.example.demo.registration;

import com.example.demo.identity.UserStatus;

public class RegisteredUser {

    private final String id;
    private final String email;
    private final String password; // 🔐 hash BCrypt
    private final String role;
    private UserStatus status;

    public RegisteredUser(
            String id,
            String email,
            String password,
            String role,
            UserStatus status
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    // ===== GETTERS =====

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
