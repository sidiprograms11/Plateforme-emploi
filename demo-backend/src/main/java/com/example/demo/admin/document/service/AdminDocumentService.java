package com.example.demo.admin.document.service;

import com.example.demo.admin.document.dto.AdminDocumentDTO;
import com.example.demo.cryptoenv.persistence.DocumentRepository;
import com.example.demo.registration.persistence.RegisteredUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDocumentService {

    private final DocumentRepository documentRepository;
    private final RegisteredUserRepository userRepository;

    public AdminDocumentService(
            DocumentRepository documentRepository,
            RegisteredUserRepository userRepository
    ) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    public List<AdminDocumentDTO> getAllDocuments() {

        return documentRepository.findAll()
                .stream()
                .map(doc -> new AdminDocumentDTO(

                        doc.getId(),

                        doc.getFileName(),

                        userRepository.findById(doc.getOwnerId())
                                .map(user -> user.getEmail())
                                .orElse("Unknown"),

                        doc.getFileSize(),

                        doc.getUploadedAt(),

                        doc.getAlgorithm(),

                        doc.getHash()

                ))
                .toList();
    }

    public void deleteDocument(String id) {
        documentRepository.deleteById(id);
    }

}