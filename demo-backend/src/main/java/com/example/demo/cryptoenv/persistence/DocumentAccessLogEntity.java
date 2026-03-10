package com.example.demo.cryptoenv.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_access_log")
public class DocumentAccessLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String documentId;

    @Column(nullable = false)
    private String actorId; // Celui qui fait l'action

    @Column(nullable = false)
    private String actionType; // UPLOAD, SHARE, READ, SIGN, DELETE, REMOVE_SHARE

    private String targetUserId; // Utilisé pour SHARE / REMOVE_SHARE

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public DocumentAccessLogEntity() {}

    public DocumentAccessLogEntity(
            String documentId,
            String actorId,
            String actionType,
            String targetUserId
    ) {
        this.documentId = documentId;
        this.actorId = actorId;
        this.actionType = actionType;
        this.targetUserId = targetUserId;
        this.timestamp = LocalDateTime.now(); // 🔥 automatique
    }

    // ======================
    // GETTERS
    // ======================

    public String getId() { return id; }
    public String getDocumentId() { return documentId; }
    public String getActorId() { return actorId; }
    public String getActionType() { return actionType; }
    public String getTargetUserId() { return targetUserId; }
    public LocalDateTime getTimestamp() { return timestamp; }
}