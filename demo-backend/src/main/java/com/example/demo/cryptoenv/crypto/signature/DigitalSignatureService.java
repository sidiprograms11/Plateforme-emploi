package com.example.demo.cryptoenv.crypto.signature;

import java.security.*;

public class DigitalSignatureService {

    private static final String ALGORITHM = "SHA256withRSA";

    public byte[] sign(byte[] data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance(ALGORITHM);
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException("Erreur signature", e);
        }
    }

    public boolean verify(byte[] data, byte[] signatureBytes, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance(ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(signatureBytes);
        } catch (Exception e) {
            throw new RuntimeException("Erreur vérification", e);
        }
    }
}
