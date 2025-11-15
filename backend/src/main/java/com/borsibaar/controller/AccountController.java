package com.borsibaar.controller;

import com.borsibaar.entity.User;
import com.borsibaar.repository.UserRepository;
import com.borsibaar.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final UserRepository userRepository;

    public record MeResponse(String email, String name, String role, Long organizationId, boolean needsOnboarding) {
    }

    public record onboardingRequest(Long organizationId, boolean acceptTerms) {
    }

    @GetMapping
    public ResponseEntity<MeResponse> me() {
        try {
            // Allow users without organization (for onboarding check)
            User user = SecurityUtils.getCurrentUser(false);

            return ResponseEntity.ok(new MeResponse(
                    user.getEmail(),
                    user.getName(),
                    user.getRole() != null ? user.getRole().getName() : null,
                    user.getOrganizationId(),
                    user.getOrganizationId() == null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @PostMapping("/onboarding")
    @Transactional
    public ResponseEntity<Void> finish(@RequestBody onboardingRequest req) {
        if (req.organizationId() == null || !req.acceptTerms())
            return ResponseEntity.badRequest().build();

        // Allow users without organization (that's the point of onboarding)
        User user = SecurityUtils.getCurrentUser(false);

        // Set org only (idempotent: do nothing if already set)
        if (user.getOrganizationId() == null) {
            user.setOrganizationId(req.organizationId());
            userRepository.save(user);
        }

        // If later you add orgId to JWT, re-issue token here.
        return ResponseEntity.noContent().build();
    }

}
