package com.example.demo.cryptoenv.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentKeyAccessRepository
        extends JpaRepository<DocumentKeyAccessEntity, Long> {

    Optional<DocumentKeyAccessEntity>
    findByDocumentIdAndUserId(String documentId, String userId);
}
