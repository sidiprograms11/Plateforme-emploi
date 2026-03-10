package com.example.demo.cryptoenv.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "document_key_access")
public class DocumentKeyAccessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String documentId;

    @Column(nullable = false)
    private String userId;

    @Lob
    @Column(nullable = false)
    private byte[] encryptedAesKey;

    public DocumentKeyAccessEntity() {
    }

    public DocumentKeyAccessEntity(String documentId,
                                   String userId,
                                   byte[] encryptedAesKey) {
        this.documentId = documentId;
        this.userId = userId;
        this.encryptedAesKey = encryptedAesKey;
    }

    public Long getId() {
        return id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getUserId() {
        return userId;
    }

    public byte[] getEncryptedAesKey() {
        return encryptedAesKey;
    }
}
