package com.example.demo.offer;

import com.example.demo.identity.UserStatus;
import com.example.demo.offer.persistence.*;
import com.example.demo.registration.model.Role;
import com.example.demo.registration.persistence.RegisteredUserEntity;
import com.example.demo.registration.persistence.RegisteredUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final OfferRepository offerRepository;
    private final RegisteredUserRepository userRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              OfferRepository offerRepository,
                              RegisteredUserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    // ==================================================
    // 📝 USER POSTULE
    // ==================================================
    public ApplicationEntity apply(String offerId, String userId) {

        RegisteredUserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (user.getRole() != Role.USER) {
            throw new RuntimeException("Seul un USER peut postuler");
        }

        if (user.getStatus() != UserStatus.VERIFIED) {
            throw new RuntimeException("Utilisateur non vérifié");
        }

        offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        // Empêche double candidature
        if (applicationRepository.findByOfferIdAndUserId(offerId, userId).isPresent()) {
            throw new RuntimeException("Vous avez déjà postulé à cette offre");
        }

        ApplicationEntity application = new ApplicationEntity(offerId, userId);
        return applicationRepository.save(application);
    }
    // ==================================================
// 📋 MES CANDIDATURES (USER)
// ==================================================
    public java.util.List<ApplicationEntity> getMyApplications(String userId) {
        return applicationRepository.findByUserId(userId);
    }
    // ==================================================
    // ✅ APPROVE
    // ==================================================
    public ApplicationEntity approveApplication(String applicationId, String adminId) {

        ApplicationEntity application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        OfferEntity offer = offerRepository.findById(application.getOfferId())
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (!offer.getAdminId().equals(adminId)) {
            throw new RuntimeException("Accès refusé");
        }

        application.setStatus(ApplicationStatus.APPROVED);
        return applicationRepository.save(application);
    }

    // ==================================================
    // ❌ REJECT
    // ==================================================
    public ApplicationEntity rejectApplication(String applicationId, String adminId) {

        ApplicationEntity application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        OfferEntity offer = offerRepository.findById(application.getOfferId())
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (!offer.getAdminId().equals(adminId)) {
            throw new RuntimeException("Accès refusé");
        }

        application.setStatus(ApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }
}