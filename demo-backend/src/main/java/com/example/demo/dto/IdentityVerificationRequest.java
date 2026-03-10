package com.example.demo.dto;

public record IdentityVerificationRequest(
        String userId,
        String idDocumentBase64,
        String selfieBase64
) {}
