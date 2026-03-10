package com.example.demo.cryptoenv.api.dto;

import java.time.LocalDateTime;

public class DocumentHistoryResponse {

    private String documentId;
    private String documentTitle;
    private String documentType;

    private String ownerId;
    private String ownerEmail;

    private String actionType;

    private String actorId;
    private String actorEmail;

    private String targetUserId;
    private String targetUserEmail;

    private int totalAuthorizedUsers;

    private LocalDateTime timestamp;

    public DocumentHistoryResponse(
            String documentId,
            String documentTitle,
            String documentType,
            String ownerId,
            String ownerEmail,
            String actionType,
            String actorId,
            String actorEmail,
            String targetUserId,
            String targetUserEmail,
            int totalAuthorizedUsers,
            LocalDateTime timestamp
    ) {
        this.documentId = documentId;
        this.documentTitle = documentTitle;
        this.documentType = documentType;
        this.ownerId = ownerId;
        this.ownerEmail = ownerEmail;
        this.actionType = actionType;
        this.actorId = actorId;
        this.actorEmail = actorEmail;
        this.targetUserId = targetUserId;
        this.targetUserEmail = targetUserEmail;
        this.totalAuthorizedUsers = totalAuthorizedUsers;
        this.timestamp = timestamp;
    }

    public String getDocumentId() { return documentId; }
    public String getDocumentTitle() { return documentTitle; }
    public String getDocumentType() { return documentType; }
    public String getOwnerId() { return ownerId; }
    public String getOwnerEmail() { return ownerEmail; }
    public String getActionType() { return actionType; }
    public String getActorId() { return actorId; }
    public String getActorEmail() { return actorEmail; }
    public String getTargetUserId() { return targetUserId; }
    public String getTargetUserEmail() { return targetUserEmail; }
    public int getTotalAuthorizedUsers() { return totalAuthorizedUsers; }
    public LocalDateTime getTimestamp() { return timestamp; }
}