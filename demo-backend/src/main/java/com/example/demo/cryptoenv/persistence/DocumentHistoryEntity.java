package com.example.demo.cryptoenv.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_history")
public class DocumentHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String documentId;

    @Column(nullable = false)
    private String actionType;
    // UPLOAD, READ, SIGN, SHARE, DELETE

    @Column(nullable = false)
    private String actorId;
    // qui fait l’action

    private String targetUserId;
    // si partage

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public DocumentHistoryEntity() {}

    public DocumentHistoryEntity(
            String documentId,
            String actionType,
            String actorId,
            String targetUserId,
            LocalDateTime timestamp
    ) {
        this.documentId = documentId;
        this.actionType = actionType;
        this.actorId = actorId;
        this.targetUserId = targetUserId;
        this.timestamp = timestamp;
    }

    // GETTERS & SETTERS
}