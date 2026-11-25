package com.Atos.Webaccess.Approve.service;

import com.Atos.Webaccess.User.Entities.Right;
import com.Atos.Webaccess.User.Entities.Role;
import com.Atos.Webaccess.User.Entities.User;
import com.Atos.Webaccess.User.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CurrentUserProvider {

    private final UserRepository userRepository;

    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Authentication is required.");
        }
        User user = userRepository.findUserByUsername(authentication.getName());
        if (user == null) {
            throw new AccessDeniedException("Authenticated user was not found.");
        }
        return user;
    }

    public Set<Long> activeRightIds(User user) {
        if (user.getAuthorites() == null) {
            return Set.of();
        }
        return user.getAuthorites().stream()
                .filter(Role.class::isInstance)
                .map(Role.class::cast)
                .filter(role -> role.getRights() != null)
                .flatMap(role -> role.getRights().stream())
                .filter(this::isActive)
                .map(Right::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private boolean isActive(Right right) {
        return Integer.valueOf(1).equals(right.getStatus())
                && Boolean.parseBoolean(right.getValue());
    }
}