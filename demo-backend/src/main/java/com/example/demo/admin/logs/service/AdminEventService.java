package com.example.demo.admin.logs.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AdminEventService {

    private final SimpMessagingTemplate messagingTemplate;

    public AdminEventService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // ===============================
    // MÉTHODE GÉNÉRALE
    // ===============================

    private void sendEvent(String message) {

        messagingTemplate.convertAndSend(
                "/topic/admin-events",
                message
        );
    }

    // ===============================
    // UTILISATEURS
    // ===============================

    public void userRegistered(String email) {

        sendEvent("👤 Nouvel utilisateur inscrit : " + email);

    }

    public void userSuspended(String email) {

        sendEvent("⛔ Compte suspendu : " + email);

    }

    public void userPromoted(String email) {

        sendEvent("👑 Nouvel admin promu : " + email);

    }

    // ===============================
    // DOCUMENTS
    // ===============================

    public void documentUploaded(String email) {

        sendEvent("📄 Document upload par : " + email);

    }

    public void documentShared(String targetEmail) {

        sendEvent("🔗 Document partagé avec : " + targetEmail);

    }

    public void documentDeleted(String documentId) {

        sendEvent("🗑 Document supprimé par admin : " + documentId);

    }

    // ===============================
    // IDENTITÉ
    // ===============================

    public void identitySubmitted(String email) {

        sendEvent("🪪 Nouvelle demande de vérification : " + email);

    }

    public void identityApproved(String email) {

        sendEvent("✅ Identité approuvée : " + email);

    }

    public void identityRejected(String email) {

        sendEvent("❌ Identité rejetée : " + email);

    }

    // ===============================
    // SÉCURITÉ
    // ===============================

    public void unauthorizedAccess(String email) {

        sendEvent("⚠ Tentative accès non autorisé : " + email);

    }

}