package com.example.demo.cryptoenv.coffre;

import com.example.demo.crypto.KeyEncryptionService;
import com.example.demo.cryptoenv.crypto.encryption.*;
import com.example.demo.cryptoenv.crypto.key.SymmetricKeyService;
import com.example.demo.cryptoenv.persistence.*;
import com.example.demo.registration.persistence.RegisteredUserEntity;
import com.example.demo.registration.persistence.RegisteredUserRepository;
import com.example.demo.cryptoenv.persistence.DocumentSignatureRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CoffreService {

    private final StoredDocumentRepository documentRepository;
    private final DocumentKeyAccessRepository keyAccessRepository;
    private final RegisteredUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KeyEncryptionService keyEncryptionService;
    private final DocumentSignatureRepository signatureRepository;

    private final SymmetricEncryptionService symmetricEncryptionService = new SymmetricEncryptionService();
    private final AsymmetricEncryptionService asymmetricEncryptionService = new AsymmetricEncryptionService();
    private final SymmetricKeyService symmetricKeyService = new SymmetricKeyService();

    public CoffreService(
            StoredDocumentRepository documentRepository,
            DocumentKeyAccessRepository keyAccessRepository,
            RegisteredUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            KeyEncryptionService keyEncryptionService,
            DocumentSignatureRepository signatureRepository
    ) {
        this.documentRepository = documentRepository;
        this.keyAccessRepository = keyAccessRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.keyEncryptionService = keyEncryptionService;
        this.signatureRepository = signatureRepository;
    }

    // ==================================================
    // 🔐 DEPOT DOCUMENT
    // ==================================================

    public String deposerDossier(
            byte[] document,
            String ownerId,
            String documentType,
            String origine,
            List<String> authorizedUserIds
    ) {

        try {

            RegisteredUserEntity owner = userRepository.findById(ownerId)
                    .orElseThrow(() -> new RuntimeException("Owner not found"));

            // SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(document);
            String hashBase64 = Base64.getEncoder().encodeToString(hashBytes);

            // AES key
            SecretKey aesKey = symmetricKeyService.generateKey();
            EncryptedData encryptedData =
                    symmetricEncryptionService.encrypt(document, aesKey);



            String documentId = UUID.randomUUID().toString();

            StoredDocumentEntity entity = new StoredDocumentEntity();

            entity.setId(documentId);
            entity.setOwnerId(ownerId);

            entity.setDocumentType(documentType);
            entity.setOrigine(origine);

            entity.setFileName("document_" + documentId); // 🔥 OBLIGATOIRE
            entity.setFileSize((long) document.length);
            entity.setUploadedAt(LocalDateTime.now());

            entity.setAlgorithm("AES-256");
            entity.setHash(hashBase64);
            entity.setEncryptedDocument(encryptedData.getCipherText());
            entity.setIv(encryptedData.getIv());

            entity.setSigned(false); // safe

            documentRepository.save(entity);

            if (authorizedUserIds == null) {
                authorizedUserIds = new ArrayList<>();
            }

            if (!authorizedUserIds.contains(ownerId)) {
                authorizedUserIds.add(ownerId);
            }

            for (String userId : authorizedUserIds) {

                RegisteredUserEntity user =
                        userRepository.findById(userId).orElseThrow();

                PublicKey publicKey =
                        asymmetricEncryptionService.rebuildPublicKey(user.getPublicKey());

                byte[] encryptedAesKey =
                        asymmetricEncryptionService.encrypt(
                                aesKey.getEncoded(),
                                publicKey
                        );

                keyAccessRepository.save(
                        new DocumentKeyAccessEntity(documentId, userId, encryptedAesKey)
                );
            }

            return documentId;

        } catch (Exception e) {
            throw new RuntimeException("Erreur dépôt document", e);
        }
    }

    // ==================================================
    // ✍️ SIGNATURE
    // ==================================================

    public void signerDocument(String documentId, String userId, String password) {

        try {

            StoredDocumentEntity document =
                    documentRepository.findById(documentId)
                            .orElseThrow(() -> new RuntimeException("Document introuvable"));

            RegisteredUserEntity user =
                    userRepository.findById(userId)
                            .orElseThrow();

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Mot de passe incorrect");
            }

            // 🔓 Déchiffrement clé privée
            byte[] privateKeyBytes =
                    keyEncryptionService.decryptPrivateKey(
                            user.getEncryptedPrivateKey(),
                            password,
                            Base64.getDecoder().decode(user.getPrivateKeySalt())
                    );

            PrivateKey privateKey =
                    asymmetricEncryptionService.rebuildPrivateKey(
                            Base64.getEncoder().encodeToString(privateKeyBytes)
                    );

            byte[] hashBytes =
                    Base64.getDecoder().decode(document.getHash());

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(hashBytes);

            byte[] digitalSignature = signature.sign();

            // 🔥 ON NE BLOQUE PLUS SI DÉJÀ SIGNÉ

            DocumentSignatureEntity signatureEntity =
                    new DocumentSignatureEntity(documentId, userId, digitalSignature);

            signatureRepository.save(signatureEntity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur signature", e);
        }
    }

    // ==================================================
    // 🔓 LECTURE
    // ==================================================
public boolean verifierSignature(String documentId) {

        try {

            StoredDocumentEntity document =
                    documentRepository.findById(documentId)
                            .orElseThrow();

            if (!document.isSigned()) {
                return false;
            }

            RegisteredUserEntity owner =
                    userRepository.findById(document.getOwnerId())
                            .orElseThrow();

            PublicKey publicKey =
                    asymmetricEncryptionService.rebuildPublicKey(
                            owner.getPublicKey()
                    );

            Signature verifier =
                    Signature.getInstance("SHA256withRSA");

            verifier.initVerify(publicKey);

            byte[] hashBytes =
                    Base64.getDecoder().decode(document.getHash());

            verifier.update(hashBytes);

            return verifier.verify(document.getSignature());

        } catch (Exception e) {
            return false;
        }
    }
    @Transactional(readOnly = true)
    public byte[] lireDocument(
            String documentId,
            String userId,
            String password
    ) {

        try {
            System.out.println("DOCUMENT ID = [" + documentId + "]");
            System.out.println("USER ID JWT = [" + userId + "]");
            System.out.println("PASSWORD = [" + password + "]");
            StoredDocumentEntity document =
                    documentRepository.findById(documentId)
                            .orElseThrow(() -> new RuntimeException("Document introuvable"));

            Optional<DocumentKeyAccessEntity> test =
                    keyAccessRepository.findByDocumentIdAndUserId(documentId, userId);

            System.out.println("ACCESS FOUND = " + test.isPresent());
            DocumentKeyAccessEntity access =
                    keyAccessRepository
                            .findByDocumentIdAndUserId(documentId, userId)
                            .orElseThrow(() -> new RuntimeException("Accès refusé"));

            RegisteredUserEntity user =
                    userRepository.findById(userId)
                            .orElseThrow();

            // 🔐 Vérification password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Mot de passe incorrect");
            }

            // 🔐 Déchiffrement clé privée protégée
            byte[] privateKeyBytes =
                    keyEncryptionService.decryptPrivateKey(
                            user.getEncryptedPrivateKey(),
                            password,
                            Base64.getDecoder().decode(user.getPrivateKeySalt())
                    );

            PKCS8EncodedKeySpec spec =
                    new PKCS8EncodedKeySpec(privateKeyBytes);

            KeyFactory keyFactory =
                    KeyFactory.getInstance("RSA");

            PrivateKey privateKey =
                    keyFactory.generatePrivate(spec);

            // 🔓 Déchiffrement clé AES
            byte[] decryptedAesKeyBytes =
                    asymmetricEncryptionService.decrypt(
                            access.getEncryptedAesKey(),
                            privateKey
                    );

            SecretKey aesKey =
                    symmetricKeyService.rebuildKey(decryptedAesKeyBytes);

            EncryptedData encryptedData =
                    new EncryptedData(
                            document.getEncryptedDocument(),
                            document.getIv()
                    );

            return symmetricEncryptionService.decrypt(encryptedData, aesKey);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lecture document", e);
        }
    }


}