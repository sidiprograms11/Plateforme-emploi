package com.example.demo.cryptoenv.api;

import com.example.demo.cryptoenv.api.dto.DocumentReadRequest;
import com.example.demo.cryptoenv.api.dto.DocumentSummaryResponse;
import com.example.demo.cryptoenv.api.dto.UserSearchResponse;
import com.example.demo.cryptoenv.document.DocumentService;
import com.example.demo.cryptoenv.document.SecureDocumentType;
import com.example.demo.cryptoenv.dto.SignDocumentRequest;
import com.example.demo.cryptoenv.persistence.StoredDocumentEntity;
import com.example.demo.cryptoenv.persistence.StoredDocumentRepository;
import com.example.demo.registration.persistence.RegisteredUserRepository;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:4200")
public class UserDocumentController {

    private final DocumentService documentService;
    private final RegisteredUserRepository registeredUserRepository;

    public UserDocumentController(
            DocumentService documentService,
            RegisteredUserRepository registeredUserRepository
    ) {
        this.documentService = documentService;
        this.registeredUserRepository = registeredUserRepository;
    }

    // ==================================================
    // 📤 UPLOAD
    // ==================================================

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @RequestParam("documentType") String documentType,
            @RequestParam("origine") String origine,
            @RequestParam("title") String title,   // ✅ AJOUTÉ
            @RequestParam(value = "authorizedUserIds", required = false) List<String> authorizedUserIds,
            Authentication authentication
    ) throws Exception {

        String userId = authentication.getName();
        System.out.println("AUTH USER = " + userId);
        SecureDocumentType type = SecureDocumentType.from(documentType);

        List<String> authorized =
                authorizedUserIds != null
                        ? authorizedUserIds
                        : Collections.singletonList(userId);

        if (!authorized.contains(userId)) {
            System.out.println("JWT authentication.getName() = " + authentication.getName());
            authorized.add(userId);
        }

        String documentId = documentService.deposerDocument(
                file.getBytes(),
                file.getContentType(),
                userId,
                type,
                origine,
                authorized,
                title   // ✅ PASSAGE DU TITLE
        );

        return ResponseEntity.ok(
                Map.of(
                        "message", "Document ajouté au coffre",
                        "documentId", documentId
                )
        );
    }

    // ==================================================
    // 📂 MES DOCUMENTS
    // ==================================================

    @GetMapping("/me")
    public List<DocumentSummaryResponse> getMyDocuments(Authentication authentication) {

        String userId = authentication.getName();

        return documentService.getMyDocuments(userId);
    }
    @GetMapping("/shared-by-me")
    public List<DocumentSummaryResponse> getSharedByMe(Authentication authentication) {

        String userId = authentication.getName();

        return documentService.getSharedByMe(userId);
    }

    // ==================================================
    // 📂 DOCUMENTS PARTAGÉS AVEC MOI
    // ==================================================

    @GetMapping("/shared-with-me")
    public List<DocumentSummaryResponse> getSharedWithMe(Authentication authentication) {

        String userId = authentication.getName();

        return documentService.getSharedWithMe(userId);
    }

    // ==================================================
    // 🔓 LIRE DOCUMENT
    // ==================================================

    @PostMapping("/{id}/read")
    public ResponseEntity<byte[]> readDocument(
            @PathVariable String id,
            @RequestBody DocumentReadRequest request,
            Authentication authentication
    ) {

        String userId = authentication.getName();

        byte[] documentBytes =
                documentService.lireDocument(id, userId, request.getPassword());

        StoredDocumentEntity stored =
                documentService.getStoredDocument(id);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(stored.getContentType()))
                .body(documentBytes);
    }

    // ==================================================
    // ✍️ SIGNER
    // ==================================================

    @PostMapping("/{id}/sign")
    public ResponseEntity<?> signDocument(
            @PathVariable String id,
            @RequestBody SignDocumentRequest request,
            Authentication authentication
    ) {

        String userId = authentication.getName();

        documentService.signerDocument(id, userId, request.getPassword());

        return ResponseEntity.ok(
                Map.of("message", "Document signé avec succès")
        );
    }

    // ==================================================
    // 🔎 VERIFY
    // ==================================================

    @GetMapping("/{id}/verify")
    public ResponseEntity<?> verifySignature(
            @PathVariable String id,
            Authentication authentication
    ) {

        String userId = authentication.getName();

        boolean valid = documentService.verifierSignature(id, userId);

        return ResponseEntity.ok(
                Map.of("valid", valid)
        );
    }

    // ==================================================
    // 👥 PARTAGER DOCUMENT
    // ==================================================

    @PostMapping("/{id}/share")
    public ResponseEntity<?> shareDocument(
            @PathVariable String id,
            @RequestBody Map<String, String> body,
            Authentication authentication
    ) {

        String ownerId = authentication.getName();
        String targetUserId = body.get("userId");

        documentService.shareDocument(id, ownerId, targetUserId);

        return ResponseEntity.ok(
                Map.of("message", "Document partagé avec succès")
        );
    }

    // ==================================================
    // 👥 RETIRER PARTAGE
    // ==================================================

    @DeleteMapping("/{id}/share/{userId}")
    public ResponseEntity<?> removeShare(
            @PathVariable String id,
            @PathVariable String userId,
            Authentication authentication
    ) {

        String ownerId = authentication.getName();

        documentService.removeShare(id, ownerId, userId);

        return ResponseEntity.ok(
                Map.of("message", "Accès retiré avec succès")
        );
    }

    // ==================================================
    // 👥 LISTE UTILISATEURS AUTORISÉS
    // ==================================================

    @GetMapping("/{id}/authorized-users")
    public ResponseEntity<?> getAuthorizedUsers(
            @PathVariable String id,
            Authentication authentication
    ) {

        String userId = authentication.getName();

        StoredDocumentEntity document =
                documentService.getStoredDocument(id);

        if (!document.getOwnerId().equals(userId)) {
            return ResponseEntity.status(403)
                    .body(Map.of("error", "Seul le propriétaire peut voir les accès"));
        }

        List<UserSearchResponse> users =
                document.getAuthorizedUserIds()
                        .stream()
                        .map(uid -> registeredUserRepository.findById(uid).orElse(null))
                        .filter(user -> user != null)
                        .map(user -> new UserSearchResponse(
                                user.getId(),
                                user.getEmail()
                        ))
                        .toList();

        return ResponseEntity.ok(users);
    }

    // ==================================================
    // 🗑 SUPPRIMER DOCUMENT
    // ==================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(
            @PathVariable String id,
            Authentication authentication
    ) {

        String userId = authentication.getName();

        documentService.deleteDocument(id, userId);

        return ResponseEntity.ok(
                Map.of("message", "Document supprimé avec succès")
        );
    }

    // ==================================================
    // 📜 HISTORIQUE DOCUMENT
    // ==================================================

    @GetMapping("/{id}/history")
    public ResponseEntity<?> getDocumentHistory(
            @PathVariable String id,
            Authentication authentication
    ) {

        String userId = authentication.getName();

        return ResponseEntity.ok(
                documentService.getDocumentHistory(id, userId)
        );
    }

}