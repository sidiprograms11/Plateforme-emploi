package com.example.demo.offer.api;

import com.example.demo.offer.ApplicationService;
import com.example.demo.offer.OfferService;
import com.example.demo.offer.persistence.ApplicationEntity;
import com.example.demo.offer.persistence.OfferEntity;
import com.example.demo.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = "http://localhost:4200")
public class OfferController {

    private final OfferService offerService;
    private final ApplicationService applicationService;
    private final JwtService jwtService;

    public OfferController(OfferService offerService,
                           ApplicationService applicationService,
                           JwtService jwtService) {
        this.offerService = offerService;
        this.applicationService = applicationService;
        this.jwtService = jwtService;
    }

    // ==================================================
    // 🔐 ADMIN crée une offre
    // ==================================================
    @PostMapping
    public OfferEntity createOffer(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody OfferRequest request
    ) {
        String token = authHeader.replace("Bearer ", "");
        String adminId = jwtService.extractUserId(token);

        return offerService.createOffer(
                adminId,
                request.getTitle(),
                request.getDescription()
        );
    }

    // ==================================================
    // 📋 Voir toutes les offres
    // ==================================================
    @GetMapping
    public List<OfferEntity> getAllOffers() {
        return offerService.getAllOffers();
    }

    // ==================================================
    // 🔍 Voir une offre spécifique
    // ==================================================
    @GetMapping("/{offerId}")
    public OfferEntity getOffer(@PathVariable String offerId) {
        return offerService.getOfferById(offerId);
    }

    // ==================================================
    // 📝 USER candidate à une offre
    // ==================================================
    @PostMapping("/{offerId}/apply")
    public ApplicationEntity applyToOffer(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String offerId
    ) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtService.extractUserId(token);

        return applicationService.apply(offerId, userId);
    }

    // ==================================================
    // 👀 ADMIN voit les candidatures de son offre
    // ==================================================
    @GetMapping("/{offerId}/applications")
    public List<ApplicationEntity> getApplicationsForOffer(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String offerId
    ) {
        String token = authHeader.replace("Bearer ", "");
        String adminId = jwtService.extractUserId(token);

        return offerService.getApplicationsForOffer(offerId, adminId);
    }

    // ==================================================
    // ✅ ADMIN approuve une candidature
    // ==================================================
    @PostMapping("/{offerId}/applications/{applicationId}/approve")
    public ApplicationEntity approveApplication(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String offerId,
            @PathVariable String applicationId
    ) {
        String token = authHeader.replace("Bearer ", "");
        String adminId = jwtService.extractUserId(token);

        return applicationService.approveApplication(applicationId, adminId);
    }

    // ==================================================
    // ❌ ADMIN rejette une candidature
    // ==================================================
    @PostMapping("/{offerId}/applications/{applicationId}/reject")
    public ApplicationEntity rejectApplication(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String offerId,
            @PathVariable String applicationId
    ) {
        String token = authHeader.replace("Bearer ", "");
        String adminId = jwtService.extractUserId(token);

        return applicationService.rejectApplication(applicationId, adminId);
    }
    // ==================================================
// ✏ ADMIN modifie une offre
// ==================================================
    @PutMapping("/{offerId}")
    public OfferEntity updateOffer(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String offerId,
            @RequestBody OfferRequest request
    ) {
        String token = authHeader.replace("Bearer ", "");
        String adminId = jwtService.extractUserId(token);

        return offerService.updateOffer(
                offerId,
                adminId,
                request.getTitle(),
                request.getDescription()
        );
    }

    // ==================================================
// 🗑 ADMIN supprime une offre
// ==================================================
    @DeleteMapping("/{offerId}")
    public void deleteOffer(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String offerId
    ) {
        String token = authHeader.replace("Bearer ", "");
        String adminId = jwtService.extractUserId(token);

        offerService.deleteOffer(offerId, adminId);
    }
}