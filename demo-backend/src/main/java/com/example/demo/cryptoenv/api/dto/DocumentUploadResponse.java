package com.example.demo.cryptoenv.api.dto;

public class DocumentUploadResponse {

    private String documentId;
    private String message;

    public DocumentUploadResponse(String documentId, String message) {
        this.documentId = documentId;
        this.message = message;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getMessage() {
        return message;
    }
}
