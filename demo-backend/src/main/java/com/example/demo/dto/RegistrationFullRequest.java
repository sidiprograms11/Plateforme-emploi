
package com.example.demo.dto;

public record RegistrationFullRequest(
        String email,
        String password,
        String role
) {}

