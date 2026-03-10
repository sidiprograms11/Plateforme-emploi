package com.example.demo.cryptoenv.api.dto;

public class DocumentSummaryResponse {

    private String id;
    private String title;
    private String documentType;
    private String origine;

    private String ownerId;
    private String ownerEmail;

    private int authorizedCount;

    private int totalSignatures;
    private boolean signedByCurrentUser;

    public DocumentSummaryResponse(
            String id,
            String title,
            String documentType,
            String origine,
            String ownerId,
            String ownerEmail,
            int authorizedCount,
            int totalSignatures,
            boolean signedByCurrentUser
    ) {
        this.id = id;
        this.title = title;
        this.documentType = documentType;
        this.origine = origine;
        this.ownerId = ownerId;
        this.ownerEmail = ownerEmail;
        this.authorizedCount = authorizedCount;
        this.totalSignatures = totalSignatures;
        this.signedByCurrentUser = signedByCurrentUser;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDocumentType() { return documentType; }
    public String getOrigine() { return origine; }
    public String getOwnerId() { return ownerId; }
    public String getOwnerEmail() { return ownerEmail; }
    public int getAuthorizedCount() { return authorizedCount; }
    public int getTotalSignatures() { return totalSignatures; }
    public boolean isSignedByCurrentUser() { return signedByCurrentUser; }
}