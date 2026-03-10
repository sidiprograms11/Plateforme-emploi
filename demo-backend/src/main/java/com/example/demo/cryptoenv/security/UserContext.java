package com.example.demo.cryptoenv.security;

import com.example.demo.identity.Permission;
import com.example.demo.identity.UserStatus;

import java.util.Set;

public class UserContext {

    private final String userId;
    private final String role;
    private final UserStatus status;
    private final Set<Permission> permissions;

    public UserContext(String userId,
                       String role,
                       UserStatus status,
                       Set<Permission> permissions) {
        this.userId = userId;
        this.role = role;
        this.status = status;
        this.permissions = permissions;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
