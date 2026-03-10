package com.example.demo.cryptoenv.document;

import com.example.demo.identity.Permission;

import java.util.Set;

public class AccessPolicy {

    private final Set<Permission> requiredPermissions;

    public AccessPolicy(Set<Permission> requiredPermissions) {
        this.requiredPermissions = requiredPermissions;
    }

    public Set<Permission> getRequiredPermissions() {
        return requiredPermissions;
    }

    public boolean canAccess(Set<Permission> userPermissions) {
        return userPermissions.stream()
                .anyMatch(requiredPermissions::contains);
    }
}
