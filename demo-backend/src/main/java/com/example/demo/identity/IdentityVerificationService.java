package com.example.demo.identity;

import java.util.Random;

public class IdentityVerificationService {

    public boolean verify(byte[] idDocument, byte[] selfie) {

        // 🎭 MOCK ADMINISTRATION
        // 80% de réussite
        return new Random().nextInt(100) < 80;
    }
}
