package com.example.demo.cryptoenv.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentAccessLogRepository
        extends JpaRepository<DocumentAccessLogEntity, String> {

    List<DocumentAccessLogEntity> findByDocumentIdOrderByTimestampDesc(String documentId);

}