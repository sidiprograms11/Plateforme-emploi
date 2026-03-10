package com.example.demo.offer.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"offer_id", "user_id"})
)
public class ApplicationEntity {

    @Id
    private String id;

    @Column(name = "offer_id", nullable = false)
    private String offerId;
    @Column(name = "document_id")
    private String documentId;

    @Column(length = 2000)
    private String motivation;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @Column(nullable = false)
    private LocalDateTime appliedAt;

    public ApplicationEntity() {}

    public ApplicationEntity(String offerId, String userId) {
        this.id = UUID.randomUUID().toString();
        this.offerId = offerId;
        this.userId = userId;
        this.status = ApplicationStatus.PENDING;
        this.appliedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getOfferId() { return offerId; }
    public String getUserId() { return userId; }
    public String getDocumentId() {
        return documentId;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }
    public ApplicationStatus getStatus() { return status; }
    public LocalDateTime getAppliedAt() { return appliedAt; }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}