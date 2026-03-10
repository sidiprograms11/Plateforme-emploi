package com.example.demo.identity;

import java.time.Instant;

public class IdentityVerification {

    private final String userId;
    private final byte[] idDocument;
    private final byte[] selfie;

    private UserStatus status;
    private final Instant submittedAt;
    private Instant verifiedAt;

    public IdentityVerification(
            String userId,
            byte[] idDocument,
            byte[] selfie
    ) {
        this.userId = userId;
        this.idDocument = idDocument;
        this.selfie = selfie;
        this.status = UserStatus.PENDING_VERIFICATION;
        this.submittedAt = Instant.now();
    }

    public void approve() {
        this.status = UserStatus.VERIFIED;
        this.verifiedAt = Instant.now();
    }

    public void reject() {
        this.status = UserStatus.REJECTED;
        this.verifiedAt = Instant.now();
    }

    // ✅ GETTERS
    public String getUserId() {
        return userId;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public Instant getVerifiedAt() {
        return verifiedAt;
    }
}
