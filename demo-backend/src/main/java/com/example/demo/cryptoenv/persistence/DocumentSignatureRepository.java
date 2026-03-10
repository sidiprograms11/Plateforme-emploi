package com.example.demo.cryptoenv.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DocumentSignatureRepository
        extends JpaRepository<DocumentSignatureEntity, String> {

    List<DocumentSignatureEntity> findByDocumentId(String documentId);

    Optional<DocumentSignatureEntity> findByDocumentIdAndUserId(
            String documentId,
            String userId
    );
}