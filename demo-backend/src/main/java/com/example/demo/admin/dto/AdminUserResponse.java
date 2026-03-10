package com.example.demo.admin.dto;

import java.time.LocalDateTime;

public class AdminUserResponse {

    private String id;
    private String email;
    private String role;
    private String status;
    private LocalDateTime createdAt;
    private long documentsCount;
    private long applicationsCount;

    public AdminUserResponse(
            String id,
            String email,
            String role,
            String status,
            LocalDateTime createdAt,
            long documentsCount,
            long applicationsCount
    ) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.documentsCount = documentsCount;
        this.applicationsCount = applicationsCount;
    }

    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public long getDocumentsCount() { return documentsCount; }
    public long getApplicationsCount() { return applicationsCount; }
}