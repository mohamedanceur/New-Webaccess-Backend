
package com.Atos.Webaccess.Approve.service;

import com.Atos.Webaccess.Approve.recherche.response.ApproveAccessResponse;
import com.Atos.Webaccess.Domain.Libelle.Libelle;
import com.Atos.Webaccess.Domain.Libelle.LibelleRepository;
import com.Atos.Webaccess.User.Entities.Right;
import com.Atos.Webaccess.User.Entities.Role;
import com.Atos.Webaccess.User.Entities.User;
import com.Atos.Webaccess.User.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApproveAccessService {

    private static final Set<Long> APPROVE_ACCESS_RIGHTS =
            Set.of(97L, 99L);

    private final UserRepository userRepository;
    private final LibelleRepository libelleRepository;

    public ApproveAccessResponse getAccessData() {

        User user = getAuthenticatedUser();

        Set<Long> rightIds = getActiveRightIds(user);

        boolean hasApproveAccess = rightIds.stream()
                .anyMatch(APPROVE_ACCESS_RIGHTS::contains);

        if (!hasApproveAccess) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "You do not have permission to access APPROVE."
            );
        }

        List<Libelle> states =
                libelleRepository.findByTypeIn(List.of("ETAT"));

        List<Libelle> rejectionStates = states.stream()
                .filter(libelle ->
                        "Rejet".equalsIgnoreCase(libelle.getLibelle3()))
                .toList();

        List<Libelle> accountStates =
                libelleRepository.findByTypeIn(List.of("ETATCOMPTE"));

        return ApproveAccessResponse.builder()
                .target("APPROVE")
                .states(states)
                .rejectionStates(rejectionStates)
                .accountStates(accountStates)
                .canValidateAllOperations(rightIds.contains(98L))
                .build();
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            throw new org.springframework.security.access.AccessDeniedException(
                    "Authentication is required."
            );
        }

        User user = userRepository.findUserByUsername(
                authentication.getName()
        );

        if (user == null) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Authenticated user was not found."
            );
        }

        return user;
    }

    private Set<Long> getActiveRightIds(User user) {

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
