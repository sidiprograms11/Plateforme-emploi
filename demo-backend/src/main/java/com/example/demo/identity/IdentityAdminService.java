package com.example.demo.identity;

import com.example.demo.registration.persistence.RegisteredUserEntity;
import com.example.demo.registration.persistence.RegisteredUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentityAdminService {

    private final RegisteredUserRepository repository;

    public IdentityAdminService(RegisteredUserRepository repository) {
        this.repository = repository;
    }

    // 🔎 Voir toutes les demandes en attente
    public List<RegisteredUserEntity> findAllPending() {
        return repository.findByStatus(UserStatus.PENDING_VERIFICATION);
    }

    // ✅ Valider une identité
    public void approve(String userId) {
        RegisteredUserEntity user = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        user.setStatus(UserStatus.VERIFIED);
        repository.save(user);
    }

    // ❌ Rejeter une identité
    public void reject(String userId) {
        RegisteredUserEntity user = repository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        user.setStatus(UserStatus.REJECTED);
        repository.save(user);
    }
}
