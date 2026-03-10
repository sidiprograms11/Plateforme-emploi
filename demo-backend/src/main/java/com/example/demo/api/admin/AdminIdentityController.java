package com.example.demo.api.admin;

import com.example.demo.identity.IdentityAdminService;
import com.example.demo.registration.persistence.RegisteredUserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/identity")
public class AdminIdentityController {

    private final IdentityAdminService identityAdminService;

    public AdminIdentityController(IdentityAdminService identityAdminService) {
        this.identityAdminService = identityAdminService;
    }

    // 🔎 Voir les utilisateurs en attente
    @GetMapping("/pending")
    public ResponseEntity<List<RegisteredUserEntity>> getPendingVerifications() {
        return ResponseEntity.ok(identityAdminService.findAllPending());
    }
    @PostMapping("/{userId}/approve")
    public ResponseEntity<String> approveUser(@PathVariable String userId) {
        identityAdminService.approve(userId);
        return ResponseEntity.ok("User approved");
    }

    @PostMapping("/{userId}/reject")
    public ResponseEntity<String> rejectUser(@PathVariable String userId) {
        identityAdminService.reject(userId);
        return ResponseEntity.ok("User rejected");
    }
}
