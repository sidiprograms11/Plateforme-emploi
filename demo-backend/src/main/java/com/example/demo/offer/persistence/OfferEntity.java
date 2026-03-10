package com.example.demo.offer.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "offers")
public class OfferEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String adminId;   // 🔥 IMPORTANT

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public OfferEntity() {}

    public OfferEntity(String title,
                       String description,
                       String adminId) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.adminId = adminId;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getAdminId() {   // 🔥 LA MÉTHODE QUI MANQUAIT
        return adminId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}