package com.borsibaar.controller;

import com.borsibaar.entity.Role;
import com.borsibaar.entity.User;
import com.borsibaar.exception.BadRequestException;
import com.borsibaar.repository.RoleRepository;
import com.borsibaar.repository.UserRepository;
import com.borsibaar.util.SecurityUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public record MeResponse(String email, String name, String role, Long organizationId, boolean needsOnboarding) {
    }

    public record onboardingRequest(Long organizationId, boolean acceptTerms) {
    }

    @GetMapping
    public ResponseEntity<MeResponse> me() {
        // Allow users without organization (for onboarding check)
        User user = SecurityUtils.getCurrentUser(false);

        return ResponseEntity.ok(new MeResponse(
                user.getEmail(),
                user.getName(),
                user.getRole() != null ? user.getRole().getName() : null,
                user.getOrganizationId(),
                user.getOrganizationId() == null));

    }

    @PostMapping("/onboarding")
    @Transactional
    public ResponseEntity<Void> finish(@RequestBody onboardingRequest req) {

        if (req.organizationId() == null || !req.acceptTerms()){
            throw new BadRequestException("organizationId is required and terms must be accepted");
        }


        // Allow users without organization (that's the point of onboarding)
        User user = SecurityUtils.getCurrentUser(false);

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new IllegalArgumentException("Admin role ADMIN not found"));

        // Set org and role if needed (idempotent: do nothing if already set)
        if (user.getOrganizationId() == null) {
            // At least one user must be admin
            if (userRepository.findByOrganizationIdAndRole(req.organizationId(), adminRole).isEmpty()) {
                user.setRole(adminRole);
            }
            user.setOrganizationId(req.organizationId());
            userRepository.save(user);
        }

        // If later you add orgId to JWT, re-issue token here.
        return ResponseEntity.noContent().build();

    }

}
