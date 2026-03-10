package com.example.demo.offer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, String> {
    long countByUserId(String userId);

    boolean existsByOfferIdAndUserId(String offerId, String userId);

    List<ApplicationEntity> findByOfferId(String offerId);

    List<ApplicationEntity> findByUserId(String userId);

    Optional<ApplicationEntity> findByOfferIdAndUserId(String offerId, String userId);
}