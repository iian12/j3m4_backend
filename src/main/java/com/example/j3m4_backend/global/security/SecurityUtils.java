package com.example.j3m4_backend.global.security;

import com.example.j3m4_backend.global.auth.CustomUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication);
    }

    public static Optional<CustomUserDetail> getCurrentUserDetails() {
        return getAuthentication()
                .map(auth -> {
                    Object principal = auth.getPrincipal();
                    if (principal instanceof CustomUserDetail) {
                        return (CustomUserDetail) principal;
                    }
                    return null;
                });
    }

    public static Optional<Long> getCurrentUserId() {
        return getCurrentUserDetails().map(CustomUserDetail::getId);
    }
}
