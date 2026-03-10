package com.example.demo.admin.service;

import com.example.demo.admin.dto.AdminUserResponse;
import com.example.demo.registration.model.Role;
import com.example.demo.registration.persistence.RegisteredUserEntity;
import com.example.demo.registration.persistence.RegisteredUserRepository;
import com.example.demo.offer.persistence.ApplicationRepository;
import com.example.demo.cryptoenv.persistence.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserService {

    private final RegisteredUserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final DocumentRepository documentRepository;

    public AdminUserService(
            RegisteredUserRepository userRepository,
            ApplicationRepository applicationRepository,
            DocumentRepository documentRepository
    ) {
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.documentRepository = documentRepository;
    }

    public List<AdminUserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> new AdminUserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getRole().name(),
                        user.getStatus().name(),
                        user.getCreatedAt(),
                        documentRepository.countByOwnerId(user.getId()),
                        applicationRepository.countByUserId(user.getId())
                ))
                .collect(Collectors.toList());
    }

    public void suspendUser(String userId) {
        RegisteredUserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(com.example.demo.identity.UserStatus.SUSPENDED);
        userRepository.save(user);
    }

    public void promoteToAdmin(String userId) {
        RegisteredUserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}