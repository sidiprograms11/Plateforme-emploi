package com.example.demo.cryptoenv.crypto.encryption;

public class EncryptedData {

    private final byte[] cipherText;
    private final byte[] iv;

    public EncryptedData(byte[] cipherText, byte[] iv) {
        this.cipherText = cipherText;
        this.iv = iv;
    }

    public byte[] getCipherText() {
        return cipherText;
    }

    public byte[] getIv() {
        return iv;
    }
}
