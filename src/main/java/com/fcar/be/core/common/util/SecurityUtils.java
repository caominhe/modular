

// # Tiện ích lấy ID người dùng đang đăng nhập từ SecurityContext
package com.fcar.be.core.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtils {
    private SecurityUtils() {}

    public static Optional<String> getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) return Optional.empty();

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return Optional.ofNullable(userDetails.getUsername());
        } else if (authentication.getPrincipal() instanceof String principal) {
            return Optional.of(principal);
        }

        return Optional.empty();
    }
}