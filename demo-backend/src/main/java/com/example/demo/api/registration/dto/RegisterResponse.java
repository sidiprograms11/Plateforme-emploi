package com.example.demo.api.registration.dto;

import com.example.demo.identity.UserStatus;

public class RegisterResponse {

    private String userId;
    private String email;
    private String role;
    private String status;

    private String publicKey;
    private String encryptedPrivateKey;
    private String privateKeySalt;

    public RegisterResponse(
            String userId,
            String email,
            String role,
            String status,
            String publicKey,
            String encryptedPrivateKey,
            String privateKeySalt
    ) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.status = status;
        this.publicKey = publicKey;
        this.encryptedPrivateKey = encryptedPrivateKey;
        this.privateKeySalt = privateKeySalt;
    }

    public String getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public String getPublicKey() { return publicKey; }
    public String getEncryptedPrivateKey() { return encryptedPrivateKey; }
    public String getPrivateKeySalt() { return privateKeySalt; }
}