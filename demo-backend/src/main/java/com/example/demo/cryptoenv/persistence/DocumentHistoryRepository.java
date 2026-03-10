package com.example.demo.cryptoenv.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentHistoryRepository
        extends JpaRepository<DocumentHistoryEntity, String> {

    List<DocumentHistoryEntity> findByDocumentIdOrderByTimestampDesc(String documentId);

}