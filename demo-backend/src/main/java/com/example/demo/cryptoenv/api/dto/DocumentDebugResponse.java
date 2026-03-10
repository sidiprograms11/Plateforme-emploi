package com.example.demo.cryptoenv.api.dto;

public class DocumentDebugResponse {

    private String documentId;
    private String encryptedDocument;
    private String encryptedSecretKey;

    public DocumentDebugResponse(
            String documentId,
            String encryptedDocument,
            String encryptedSecretKey
    ) {
        this.documentId = documentId;
        this.encryptedDocument = encryptedDocument;
        this.encryptedSecretKey = encryptedSecretKey;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getEncryptedDocument() {
        return encryptedDocument;
    }

    public String getEncryptedSecretKey() {
        return encryptedSecretKey;
    }
}
