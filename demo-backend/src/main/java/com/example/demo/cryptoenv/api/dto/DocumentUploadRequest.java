package com.example.demo.cryptoenv.api.dto;

import java.util.List;

public class DocumentUploadRequest {

    private String documentType;
    private String origine;
    private List<String> authorizedUserIds;

    public String getDocumentType() {
        return documentType;
    }

    public String getOrigine() {
        return origine;
    }

    public List<String> getAuthorizedUserIds() {
        return authorizedUserIds;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public void setAuthorizedUserIds(List<String> authorizedUserIds) {
        this.authorizedUserIds = authorizedUserIds;
    }
}