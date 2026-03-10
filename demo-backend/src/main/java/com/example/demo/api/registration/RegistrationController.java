package com.example.demo.api.registration;

import com.example.demo.api.registration.dto.LoginRequest;
import com.example.demo.api.registration.dto.LoginResponse;
import com.example.demo.api.registration.dto.RegisterResponse;
import com.example.demo.registration.AuthService;
import com.example.demo.registration.RegistrationService;
import com.example.demo.registration.persistence.RegisteredUserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AuthService authService;

    public RegistrationController(RegistrationService registrationService,
                                  AuthService authService) {
        this.registrationService = registrationService;
        this.authService = authService;
    }

    // ✅ REGISTER AVEC MULTIPART
    @PostMapping(value = "/register", consumes = "multipart/form-data")

    public ResponseEntity<RegisterResponse> register(

            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String birthDate,

            @RequestParam MultipartFile identityDocument,
            @RequestParam MultipartFile identityPhoto

    ) throws IOException {

        // Conversion fichiers → Base64
        String identityBase64 =
                Base64.getEncoder().encodeToString(identityDocument.getBytes());

        String photoBase64 =
                Base64.getEncoder().encodeToString(identityPhoto.getBytes());

        // Création utilisateur
        RegisteredUserEntity user =
                registrationService.register(
                        email,
                        password,
                        firstName,
                        lastName,
                        birthDate,
                        identityBase64,
                        photoBase64
                );

        // Réponse Swagger avec infos crypto
        RegisterResponse response =
                new RegisterResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getRole().name(),
                        user.getStatus().name(),
                        user.getPublicKey(),
                        user.getEncryptedPrivateKey(),
                        user.getPrivateKeySalt()
                );

        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}