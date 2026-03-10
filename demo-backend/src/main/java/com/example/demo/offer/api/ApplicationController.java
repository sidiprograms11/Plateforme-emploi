package com.example.demo.offer.api;

import com.example.demo.offer.ApplicationService;
import com.example.demo.offer.persistence.ApplicationEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // ==================================================
    // 👤 USER → Mes candidatures
    // ==================================================
    @GetMapping("/me")
    public List<ApplicationEntity> getMyApplications(Authentication authentication) {

        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getSubject();

        return applicationService.getMyApplications(userId);
    }
}