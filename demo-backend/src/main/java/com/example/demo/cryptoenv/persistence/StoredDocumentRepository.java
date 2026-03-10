package com.example.demo.cryptoenv.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoredDocumentRepository
        extends JpaRepository<StoredDocumentEntity, String> {

    List<StoredDocumentEntity> findByOwnerId(String ownerId);
    List<StoredDocumentEntity> findByAuthorizedUserIdsContaining(String userId);

}