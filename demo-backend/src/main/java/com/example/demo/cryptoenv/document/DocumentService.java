package com.example.demo.cryptoenv.document;

import com.example.demo.cryptoenv.api.dto.DocumentHistoryResponse;
import com.example.demo.cryptoenv.api.dto.DocumentSummaryResponse;
import com.example.demo.cryptoenv.coffre.CoffreService;
import com.example.demo.cryptoenv.persistence.*;
import com.example.demo.registration.persistence.RegisteredUserEntity;
import com.example.demo.registration.persistence.RegisteredUserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final CoffreService coffreService;
    private final StoredDocumentRepository documentRepository;
    private final DocumentAccessLogRepository logRepository;
    private final RegisteredUserRepository userRepository;
    private final DocumentSignatureRepository signatureRepository;

    public DocumentService(
            CoffreService coffreService,
            StoredDocumentRepository documentRepository,
            DocumentAccessLogRepository logRepository,
            RegisteredUserRepository userRepository,
            DocumentSignatureRepository signatureRepository
    ) {
        this.coffreService = coffreService;
        this.documentRepository = documentRepository;
        this.logRepository = logRepository;
        this.userRepository = userRepository;
        this.signatureRepository = signatureRepository; // ✅ CORRECTION
    }

    // ==================================================
    // 🔐 LOG ACTION CENTRALISÉ
    // ==================================================

    private void logAction(
            String documentId,
            String actorId,
            String actionType,
            String targetUserId
    ) {
        DocumentAccessLogEntity log =
                new DocumentAccessLogEntity(
                        documentId,
                        actorId,
                        actionType,
                        targetUserId
                );

        logRepository.save(log);
    }

    // ==================================================
    // 🔐 CHECK ACCESS
    // ==================================================

    private StoredDocumentEntity getAndCheckAccess(String documentId, String userId) {

        StoredDocumentEntity document = documentRepository
                .findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));

        if (!document.getOwnerId().equals(userId)
                && !document.getAuthorizedUserIds().contains(userId)) {
            throw new RuntimeException("Accès refusé");
        }

        return document;
    }

    // ==================================================
    // 📤 DEPOT
    // ==================================================

    public String deposerDocument(
            byte[] fileBytes,
            String contentType,
            String ownerId,
            SecureDocumentType type,
            String origine,
            List<String> authorized,
            String title
    ) {

        String documentId = coffreService.deposerDossier(
                fileBytes,
                ownerId,
                type.name(),
                origine,
                authorized
        );

        StoredDocumentEntity entity =
                documentRepository.findById(documentId)
                        .orElseThrow();

        entity.setContentType(contentType);
        entity.setTitle(title);

        if (authorized != null) {
            entity.getAuthorizedUserIds().addAll(authorized);
        }

        documentRepository.save(entity);

        logAction(documentId, ownerId, "UPLOAD", null);

        return documentId;
    }

    // ==================================================
    // 🔓 LECTURE
    // ==================================================

    public byte[] lireDocument(
            String documentId,
            String userId,
            String password
    ) {

        getAndCheckAccess(documentId, userId);

        byte[] data = coffreService.lireDocument(documentId, userId, password);

        logAction(documentId, userId, "READ", null);

        return data;
    }

    // ==================================================
    // ✍️ SIGNATURE (VERSION MULTI-SIGNATURE PROPRE)
    // ==================================================

    public void signerDocument(
            String documentId,
            String userId,
            String password
    ) {

        // Signature crypto
        coffreService.signerDocument(documentId, userId, password);

        logAction(documentId, userId, "SIGN", null);
    }

    // ==================================================
    // 🗑 SUPPRESSION
    // ==================================================

    public void deleteDocument(String documentId, String userId) {

        StoredDocumentEntity document =
                documentRepository.findById(documentId)
                        .orElseThrow();

        if (!document.getOwnerId().equals(userId)) {
            throw new RuntimeException("Seul le propriétaire peut supprimer");
        }

        logAction(documentId, userId, "DELETE", null);

        documentRepository.delete(document);
    }

    // ==================================================
    // 👥 PARTAGE
    // ==================================================

    public void shareDocument(
            String documentId,
            String ownerId,
            String targetUserId
    ) {

        StoredDocumentEntity document =
                documentRepository.findById(documentId)
                        .orElseThrow();

        if (!document.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Seul le propriétaire peut partager");
        }

        document.getAuthorizedUserIds().add(targetUserId);
        documentRepository.save(document);

        logAction(documentId, ownerId, "SHARE", targetUserId);
    }

    public void removeShare(
            String documentId,
            String ownerId,
            String targetUserId
    ) {

        StoredDocumentEntity document =
                documentRepository.findById(documentId)
                        .orElseThrow();

        if (!document.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Seul le propriétaire peut retirer");
        }

        document.getAuthorizedUserIds().remove(targetUserId);
        documentRepository.save(document);

        logAction(documentId, ownerId, "REMOVE_SHARE", targetUserId);
    }

    // ==================================================
    // 📜 HISTORIQUE COMPLET
    // ==================================================

    public List<DocumentHistoryResponse> getDocumentHistory(
            String documentId,
            String userId
    ) {

        StoredDocumentEntity document = getAndCheckAccess(documentId, userId);

        RegisteredUserEntity owner =
                userRepository.findById(document.getOwnerId()).orElse(null);

        return logRepository
                .findByDocumentIdOrderByTimestampDesc(documentId)
                .stream()
                .map(log -> {

                    RegisteredUserEntity actor =
                            userRepository.findById(log.getActorId()).orElse(null);

                    RegisteredUserEntity target =
                            log.getTargetUserId() != null
                                    ? userRepository.findById(log.getTargetUserId()).orElse(null)
                                    : null;

                    return new DocumentHistoryResponse(
                            document.getId(),
                            document.getTitle(),
                            document.getDocumentType(),
                            document.getOwnerId(),
                            owner != null ? owner.getEmail() : null,
                            log.getActionType(),
                            log.getActorId(),
                            actor != null ? actor.getEmail() : null,
                            log.getTargetUserId(),
                            target != null ? target.getEmail() : null,
                            document.getAuthorizedUserIds().size(),
                            log.getTimestamp()
                    );
                })
                .toList();
    }

    // ==================================================
    // 📂 MES DOCUMENTS
    // ==================================================

    public List<DocumentSummaryResponse> getMyDocuments(String userId) {

        return documentRepository.findByOwnerId(userId)
                .stream()
                .map(doc -> mapToSummary(doc, userId))
                .toList();
    }

    // ==================================================
    // 📂 DOCUMENTS PARTAGÉS AVEC MOI
    // ==================================================
    public List<DocumentSummaryResponse> getSharedWithMe(String userId) {

        return documentRepository.findByAuthorizedUserIdsContaining(userId)
                .stream()
                .filter(doc -> !doc.getOwnerId().equals(userId)) // 🔥 exclut le owner
                .map(doc -> mapToSummary(doc, userId))
                .toList();
    }

    // ==================================================
    // 📄 GET DOCUMENT
    // ==================================================

    public StoredDocumentEntity getStoredDocument(String id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));
    }

    // ==================================================
    // 🔎 VERIFICATION SIGNATURE
    // ==================================================

    public boolean verifierSignature(String documentId, String userId) {

        getAndCheckAccess(documentId, userId);

        return coffreService.verifierSignature(documentId);
    }

    public List<DocumentSummaryResponse> getSharedByMe(String userId) {

        return documentRepository.findByOwnerId(userId)
                .stream()
                .filter(doc -> !doc.getAuthorizedUserIds().isEmpty())
                .map(doc -> mapToSummary(doc, userId))
                .toList();
    }
    private DocumentSummaryResponse mapToSummary(
            StoredDocumentEntity document,
            String currentUserId
    ) {

        RegisteredUserEntity owner =
                userRepository.findById(document.getOwnerId()).orElse(null);

        List<DocumentSignatureEntity> signatures =
                signatureRepository.findByDocumentId(document.getId());

        int totalSignatures = signatures.size();

        boolean signedByCurrentUser =
                signatures.stream()
                        .anyMatch(s -> s.getUserId().equals(currentUserId));

        return new DocumentSummaryResponse(
                document.getId(),
                document.getTitle(),
                document.getDocumentType(),
                document.getOrigine(),
                document.getOwnerId(),
                owner != null ? owner.getEmail() : null,
                document.getAuthorizedUserIds().size(),
                totalSignatures,
                signedByCurrentUser
        );
    }
}