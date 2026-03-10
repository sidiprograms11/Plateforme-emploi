package com.example.demo.admin.document.dto;

import java.time.LocalDateTime;

public record AdminDocumentDTO(

        String id,
        String fileName,
        String ownerEmail,
        Long fileSize,
        LocalDateTime uploadedAt,
        String algorithm,
        String hash

) {}