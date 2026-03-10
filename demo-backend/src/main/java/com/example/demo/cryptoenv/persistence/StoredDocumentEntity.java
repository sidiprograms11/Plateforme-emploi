package com.example.demo.cryptoenv.persistence;

import com.example.demo.cryptoenv.document.SecureDocumentType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Entity
@Table(name = "stored_documents")
public class StoredDocumentEntity  {

    @Id
    private String id;
    private String documentType;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String fileName;


    @Column(nullable = false)
    private String origine;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @Column(nullable = false)
    private String algorithm;

    // ===============================
    // 🔐 SIGNATURE DOCUSIGN
    // ===============================

    @Column(nullable = false)
    private boolean signed = false;

    private LocalDateTime signedAt;

    private String signedBy;

    // ===============================
    // 🔒 INTEGRITY
    // ===============================

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false, columnDefinition = "bytea")
    private byte[] encryptedDocument;

    @Column(columnDefinition = "bytea")
    private byte[] encryptedSecretKey;

    @Column(nullable = false, columnDefinition = "bytea")
    private byte[] iv;

    @Column(name = "content_type")
    private String contentType;

    @Column(columnDefinition = "bytea")
    private byte[] signature;
    @ElementCollection
    @CollectionTable(
            name = "document_authorized_users",
            joinColumns = @JoinColumn(name = "document_id")
    )
    @Column(name = "user_id")
    private java.util.Set<String> authorizedUserIds = new java.util.HashSet<>();


    // ===============================
    // CONSTRUCTORS
    // ===============================

    public StoredDocumentEntity() {}

    public StoredDocumentEntity(
            String id,
            String ownerId,
            String fileName,
            String documentType,
            String origine,
            Long fileSize,
            LocalDateTime uploadedAt,
            String algorithm,
            String hash,
            byte[] encryptedDocument,
            byte[] encryptedSecretKey,
            byte[] iv,
            byte[] signature
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.fileName = fileName;
        this.origine = origine;
        this.fileSize = fileSize;
        this.uploadedAt = uploadedAt;
        this.algorithm = algorithm;
        this.hash = hash;
        this.encryptedDocument = encryptedDocument;
        this.encryptedSecretKey = encryptedSecretKey;
        this.iv = iv;
        this.signature = signature;
    }
    public String getContentType() {
        return contentType;
     }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    // ===============================
    // GETTERS
    // ===============================

    public String getId() { return id; }
    public String getTitle() {
        return title;
    }

    public String getOwnerId() { return ownerId; }
    public String getFileName() { return fileName; }
    public String getDocumentType() {
        return documentType;
    }
    public String getOrigine() { return origine; }
    public Long getFileSize() { return fileSize; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public String getAlgorithm() { return algorithm; }
    public boolean isSigned() { return signed; }
    public LocalDateTime getSignedAt() { return signedAt; }
    public String getSignedBy() { return signedBy; }
    public String getHash() { return hash; }
    public byte[] getEncryptedDocument() { return encryptedDocument; }
    public byte[] getEncryptedSecretKey() { return encryptedSecretKey; }
    public byte[] getIv() { return iv; }
    public byte[] getSignature() { return signature; }

    // ===============================
    // SETTERS
    // ===============================

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public void setSignedAt(LocalDateTime signedAt) {
        this.signedAt = signedAt;
    }
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }
    public java.util.Set<String> getAuthorizedUserIds() {
        return authorizedUserIds;
    }

    public void setAuthorizedUserIds(java.util.Set<String> authorizedUserIds) {
        this.authorizedUserIds = authorizedUserIds;
    }

}