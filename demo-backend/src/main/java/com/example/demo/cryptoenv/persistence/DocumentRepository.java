package com.example.demo.cryptoenv.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository
        extends JpaRepository<StoredDocumentEntity, String> {

    long countByOwnerId(String ownerId);

    // ✅ MES DOCUMENTS
    List<StoredDocumentEntity> findByOwnerId(String ownerId);

    // ✅ DOCUMENTS PARTAGÉS AVEC MOI
    List<StoredDocumentEntity> findByAuthorizedUserIdsContaining(String userId);
}