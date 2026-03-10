package com.example.demo.cryptoenv.crypto.encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public class SymmetricEncryptionService {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_SIZE = 16;

    // ===============================
    // 🔐 CHIFFREMENT
    // ===============================
    public EncryptedData encrypt(byte[] data, SecretKey key) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            byte[] iv = generateIv();
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

            byte[] cipherText = cipher.doFinal(data);

            return new EncryptedData(cipherText, iv);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chiffrement symétrique", e);
        }
    }

    // ===============================
    // 🔓 DÉCHIFFREMENT
    // ===============================
    public byte[] decrypt(EncryptedData encryptedData, SecretKey key) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            IvParameterSpec ivSpec =
                    new IvParameterSpec(encryptedData.getIv());

            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            // ✅ BON NOM
            return cipher.doFinal(encryptedData.getCipherText());

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du déchiffrement symétrique", e);
        }
    }

    // ===============================
    // 🔁 GÉNÉRATION IV
    // ===============================
    private byte[] generateIv() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}
