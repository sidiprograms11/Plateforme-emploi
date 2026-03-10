package com.example.demo.cryptoenv.crypto.key;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import javax.crypto.spec.SecretKeySpec;
public class SymmetricKeyService {

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;

    public SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(KEY_SIZE);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors de la génération de la clé AES", e);
        }
    }

    // ✅ AJOUT
    public SecretKey rebuildKey(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}

