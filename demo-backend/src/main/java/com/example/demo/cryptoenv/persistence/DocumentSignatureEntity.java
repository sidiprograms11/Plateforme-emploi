package com.example.demo.cryptoenv.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document_signature")
public class DocumentSignatureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String documentId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, columnDefinition = "bytea")
    private byte[] signature;

    @Column(nullable = false)
    private LocalDateTime signedAt;

    public DocumentSignatureEntity() {}

    public DocumentSignatureEntity(
            String documentId,
            String userId,
            byte[] signature
    ) {
        this.documentId = documentId;
        this.userId = userId;
        this.signature = signature;
        this.signedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getDocumentId() { return documentId; }
    public String getUserId() { return userId; }
    public byte[] getSignature() { return signature; }
    public LocalDateTime getSignedAt() { return signedAt; }
}