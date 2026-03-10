package com.example.demo.registration.persistence;

import com.example.demo.identity.UserStatus;
import com.example.demo.registration.model.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registered_users")
public class RegisteredUserEntity {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    // ===============================
    // DATES
    // ===============================

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    // ===============================
    // ROLE & STATUS
    // ===============================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    // ===============================
    // DOCUMENTS IDENTITÉ
    // ===============================

    @Column(columnDefinition = "TEXT")
    private String identityDocumentBase64;

    @Column(columnDefinition = "TEXT")
    private String identityPhotoBase64;

    // ==============
    // =================
    // CRYPTO RSA UTILISATEUR
    // ===============================

    @Column(columnDefinition = "TEXT", nullable = false)
    private String publicKey;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String encryptedPrivateKey;

    @Column(columnDefinition = "TEXT")
    private String privateKeySalt;

    // ===============================
    // CONSTRUCTEUR VIDE (JPA)
    // ===============================

    public RegisteredUserEntity() {}

    // ===============================
    // CONSTRUCTEUR COMPLET
    // ===============================

    public RegisteredUserEntity(
            String id,
            String email,
            String password,
            Role role,
            UserStatus status,
            String identityDocumentBase64,
            String identityPhotoBase64,
            String publicKey,
            String encryptedPrivateKey,
            String privateKeySalt
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.identityDocumentBase64 = identityDocumentBase64;
        this.identityPhotoBase64 = identityPhotoBase64;
        this.publicKey = publicKey;
        this.encryptedPrivateKey = encryptedPrivateKey;
        this.privateKeySalt = privateKeySalt;
    }

    // ===============================
    // AUTO SET CREATED DATE
    // ===============================

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.submittedAt == null) {
            this.submittedAt = LocalDateTime.now();
        }
    }

    // ===============================
    // GETTERS
    // ===============================

    public String getId() { return id; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public Role getRole() { return role; }

    public UserStatus getStatus() { return status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }

    public String getIdentityDocumentBase64() { return identityDocumentBase64; }

    public String getIdentityPhotoBase64() { return identityPhotoBase64; }

    public String getPublicKey() { return publicKey; }

    public String getEncryptedPrivateKey() { return encryptedPrivateKey; }

    public String getPrivateKeySalt() { return privateKeySalt; }

    // ===============================
    // SETTERS
    // ===============================

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}