package com.example.demo.identity.api;

import java.time.LocalDateTime;

public class AdminIdentityResponse {

    private String id;
    private String email;
    private String status;
    private LocalDateTime submittedAt;
    private String identityDocumentBase64;
    private String identityPhotoBase64;

    public AdminIdentityResponse(String id,
                                 String email,
                                 String status,
                                 LocalDateTime submittedAt,
                                 String identityDocumentBase64,
                                 String identityPhotoBase64) {
        this.id = id;
        this.email = email;
        this.status = status;
        this.submittedAt = submittedAt;
        this.identityDocumentBase64 = identityDocumentBase64;
        this.identityPhotoBase64 = identityPhotoBase64;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public String getIdentityDocumentBase64() {
        return identityDocumentBase64;
    }

    public String getIdentityPhotoBase64() {
        return identityPhotoBase64;
    }
}