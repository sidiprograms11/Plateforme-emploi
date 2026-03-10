package com.example.demo.cryptoenv.security;

import com.example.demo.cryptoenv.document.SecureDocumentType;
import com.example.demo.cryptoenv.persistence.StoredDocumentEntity;
import com.example.demo.identity.Permission;
import com.example.demo.identity.UserStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccessDecisionService {

    public boolean canAccess(UserContext user,
                             StoredDocumentEntity document) {

        // ❌ Identité rejetée
        if (user.getStatus() == UserStatus.REJECTED) {
            return false;
        }

        // ❌ Non vérifié
        if (user.getStatus() != UserStatus.VERIFIED) {
            return false;
        }

        // ✅ ADMIN = accès total
        if ("ADMIN".equals(user.getRole())) {
            return true;
        }

        // ✅ Propriétaire du document
        if (document.getOwnerId().equals(user.getUserId())) {
            return true;
        }

        // 🔐 Conversion STRING → ENUM
        SecureDocumentType documentType =
                SecureDocumentType.valueOf(document.getDocumentType());

        // 🔐 Permissions requises
        Set<Permission> requiredPermissions =
                documentType.getRequiredPermissions();

        return user.getPermissions().containsAll(requiredPermissions);
    }
}
