package com.example.demo.admin.document.api;

import com.example.demo.admin.document.service.AdminDocumentService;
import com.example.demo.cryptoenv.coffre.CoffreService;
import com.example.demo.cryptoenv.persistence.StoredDocumentRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/documents")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminDocumentController {

    private final AdminDocumentService service;
    private final StoredDocumentRepository repository;
    private final CoffreService coffreService;

    public AdminDocumentController(
            AdminDocumentService service,
            StoredDocumentRepository repository,
            CoffreService coffreService
    ) {
        this.service = service;
        this.repository = repository;
        this.coffreService = coffreService;
    }

    // ==================================================
    // 📄 TOUS LES DOCUMENTS (ADMIN)
    // ==================================================

    @GetMapping
    public List<?> getAll() {
        return service.getAllDocuments();
    }

    // ==================================================
    // 📄 DOCUMENTS D’UN UTILISATEUR
    // ==================================================

    @GetMapping("/user/{userId}")
    public List<Map<String, String>> getUserDocuments(
            @PathVariable String userId
    ) {
        return repository.findByOwnerId(userId)
                .stream()
                .map(doc -> Map.of(
                        "documentId", doc.getId(),
                        "fileName", doc.getFileName(),
                        "documentType", doc.getDocumentType()
                ))
                .toList();
    }

    // ==================================================
    // 🔓 LECTURE ADMIN SECURISEE (AVEC PASSWORD)
    // ==================================================

    @PostMapping("/{documentId}/{adminId}/read")
    public ResponseEntity<byte[]> getDocument(
            @PathVariable String documentId,
            @PathVariable String adminId,
            @RequestBody Map<String, String> body
    ) {

        String password = body.get("password");

        byte[] decrypted =
                coffreService.lireDocument(
                        documentId,
                        adminId,
                        password
                );

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(decrypted);
    }

    // ==================================================
    // 🗑 SUPPRESSION
    // ==================================================

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteDocument(id);
    }
}