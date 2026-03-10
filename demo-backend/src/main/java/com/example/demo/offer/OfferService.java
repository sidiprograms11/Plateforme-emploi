package com.example.demo.offer;

import com.example.demo.identity.UserStatus;
import com.example.demo.offer.persistence.ApplicationEntity;
import com.example.demo.offer.persistence.ApplicationRepository;
import com.example.demo.offer.persistence.OfferEntity;
import com.example.demo.offer.persistence.OfferRepository;
import com.example.demo.registration.model.Role;
import com.example.demo.registration.persistence.RegisteredUserEntity;
import com.example.demo.registration.persistence.RegisteredUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final ApplicationRepository applicationRepository;
    private final RegisteredUserRepository userRepository;

    public OfferService(OfferRepository offerRepository,
                        ApplicationRepository applicationRepository,
                        RegisteredUserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    // ==================================================
    // 🔐 Création d'une offre (ADMIN uniquement)
    // ==================================================
    public OfferEntity createOffer(String adminId,
                                   String title,
                                   String description) {

        RegisteredUserEntity user = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin introuvable"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Seul un ADMIN peut créer une offre");
        }

        if (user.getStatus() != UserStatus.VERIFIED) {
            throw new RuntimeException("Admin non vérifié");
        }

        OfferEntity offer = new OfferEntity(
                title,
                description,
                adminId
        );

        return offerRepository.save(offer);
    }

    // ==================================================
    // 📋 Voir toutes les offres
    // ==================================================
    public List<OfferEntity> getAllOffers() {
        return offerRepository.findAll();
    }
    public OfferEntity updateOffer(String offerId,
                                   String adminId,
                                   String title,
                                   String description) {

        OfferEntity offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (!offer.getAdminId().equals(adminId)) {
            throw new RuntimeException("Accès refusé");
        }

        offer.setTitle(title);
        offer.setDescription(description);

        return offerRepository.save(offer);
    }

    public void deleteOffer(String offerId, String adminId) {

        OfferEntity offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (!offer.getAdminId().equals(adminId)) {
            throw new RuntimeException("Accès refusé");
        }

        offerRepository.delete(offer);
    }
    // ==================================================
    // 🔍 Voir une offre spécifique
    // ==================================================
    public OfferEntity getOfferById(String offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));
    }

    // ==================================================
    // 👀 ADMIN voit les candidatures de SON offre
    // ==================================================
    public List<ApplicationEntity> getApplicationsForOffer(String offerId,
                                                           String adminId) {

        OfferEntity offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offre introuvable"));

        if (!offer.getAdminId().equals(adminId)) {
            throw new RuntimeException("Accès refusé : vous n'êtes pas propriétaire de cette offre");
        }

        return applicationRepository.findByOfferId(offerId);
    }
}