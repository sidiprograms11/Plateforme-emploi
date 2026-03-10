package com.example.demo.registration;

import com.example.demo.crypto.KeyEncryptionService;
import com.example.demo.identity.UserStatus;
import com.example.demo.registration.model.Role;
import com.example.demo.registration.persistence.RegisteredUserEntity;
import com.example.demo.registration.persistence.RegisteredUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class RegistrationService {

    private final RegisteredUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(RegisteredUserRepository repository,
                               PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisteredUserEntity register(
            String email,
            String password,
            String firstName,
            String lastName,
            String birthDate,
            String identityBase64,
            String photoBase64
    ) {

        if (repository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        try {

            // 🔐 Génération RSA
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();

            String publicKeyBase64 =
                    Base64.getEncoder().encodeToString(
                            keyPair.getPublic().getEncoded()
                    );

            // 🔐 Génération SALT
            byte[] salt = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            String saltBase64 = Base64.getEncoder().encodeToString(salt);

            // 🔐 Chiffrement clé privée avec mot de passe
            String encryptedPrivateKey =
                    KeyEncryptionService.encryptPrivateKey(
                            keyPair.getPrivate().getEncoded(),
                            password,
                            salt
                    );

            // 🔐 Hash du mot de passe pour login
            String encodedPassword =
                    passwordEncoder.encode(password);

            RegisteredUserEntity user = new RegisteredUserEntity(
                    UUID.randomUUID().toString(),
                    email,
                    encodedPassword,
                    Role.USER,
                    UserStatus.PENDING_VERIFICATION,
                    identityBase64,
                    photoBase64,
                    publicKeyBase64,
                    encryptedPrivateKey,
                    saltBase64
            );

            user.setSubmittedAt(LocalDateTime.now());

            return repository.save(user);

        } catch (Exception e) {
            throw new RuntimeException("Erreur génération clés RSA", e);
        }
    }
}