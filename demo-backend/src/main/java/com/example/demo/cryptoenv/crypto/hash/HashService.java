package com.example.demo.cryptoenv.crypto.hash;

import java.security.MessageDigest;
import java.util.HexFormat;

public class HashService {

    public static String sha256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new IllegalStateException("Erreur hash SHA-256", e);
        }
    }
}
