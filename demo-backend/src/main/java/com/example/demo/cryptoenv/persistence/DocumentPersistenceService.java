package com.example.demo.cryptoenv.persistence;

import org.springframework.stereotype.Service;

@Service
public class DocumentPersistenceService {

    private final StoredDocumentRepository repository;

    public DocumentPersistenceService(StoredDocumentRepository repository) {
        this.repository = repository;
    }

    // =========================
    // 🔍 RÉCUPÉRATION PAR ID
    // =========================
    public StoredDocumentEntity findById(String documentId) {
        return repository.findById(documentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Document introuvable : " + documentId)
                );
    }

    // =========================
    // 📄 DOCUMENTS D'UN USER
    // =========================
    public java.util.List<StoredDocumentEntity> findByUserId(String userId) {
        return repository.findByOwnerId(userId);
    }
}
