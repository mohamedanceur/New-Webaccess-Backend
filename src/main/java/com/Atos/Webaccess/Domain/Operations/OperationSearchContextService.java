package com.Atos.Webaccess.Domain.Operations;

import com.Atos.Webaccess.Domain.Agence.Agence;
import com.Atos.Webaccess.Domain.Agence.AgenceRepository;
import com.Atos.Webaccess.Domain.Banque.Banque;
import com.Atos.Webaccess.Domain.Banque.BanqueRepository;
import com.Atos.Webaccess.Domain.Operations.Specifications.OperationSpecifications.SearchContext;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperationSearchContextService {

    private final UserRepository userRepository;
    private final AgenceRepository agenceRepository;
    private final BanqueRepository banqueRepository;
    private final OperationTypeCodeProperties operationTypeCodes;

    /*
     * Builds the search context using the authenticated user.
     *
     * Important:
     * The client must never provide this context.
     */
    public SearchContext getCurrentUserContext() {

        User user = getAuthenticatedUser();

        // 1. Retrieve the rights assigned through the user's roles.
        Set<Integer> rightIds = getActiveRightIds(user);

        // 2. Retrieve all banks and construct:
        //    bank ID -> bank code.
        List<Banque> banques = banqueRepository.findAllByOrderByLibelleAsc();

        Map<Integer, String> bankCodes = new HashMap<>();

        for (Banque banque : banques) {
            if (banque.getId() != null && banque.getCode() != null) {
                bankCodes.put(banque.getId(), banque.getCode());
            }
        }

        // 3. Resolve the authenticated user's bank.
        if (user.getIdBanque() == null) {
            throw new IllegalStateException(
                    "The authenticated user has no bank assigned."
            );
        }

        Integer userBankId = Math.toIntExact(user.getIdBanque());

        String currentBankCode = bankCodes.get(userBankId);

        if (currentBankCode == null || currentBankCode.isBlank()) {
            throw new IllegalStateException(
                    "Unable to resolve the bank code for bank ID: " + userBankId
            );
        }

        // 4. Retrieve agencies belonging to the user's group.
        Integer groupId = Math.toIntExact(user.getIdGroupe());

        List<String> authorizedAgencies =
                agenceRepository.findAgencesByGroupeId(groupId)
                        .stream()
                        .map(Agence::getCode)
                        .filter(Objects::nonNull)
                        .distinct()
                        .toList();

        // 5. Retrieve all agencies.
        //    The legacy listAllAgence() did not filter by status.
        List<String> allAgencies =
                agenceRepository.findAll()
                        .stream()
                        .map(Agence::getCode)
                        .filter(Objects::nonNull)
                        .distinct()
                        .toList();

        // 6. Validate operation type configuration.
        validateOperationTypeCodes(rightIds);

        // 7. Build the context consumed by OperationSpecifications.
        return new SearchContext(
                currentBankCode,

                authorizedAgencies,
                allAgencies,
                bankCodes,

                // Search permissions
                hasRight(rightIds, 51), // rech_carte
                hasRight(rightIds, 56), // rech_cheq
                hasRight(rightIds, 62), // rech_effet
                hasRight(rightIds, 67), // rech_prelev
                hasRight(rightIds, 72), // rech_virm
                hasRight(rightIds, 78), // rech_pMobile

                // Operation type codes from legacy Param configuration
                operationTypeCodes.getCardPayment(),
                operationTypeCodes.getCardWithdrawal(),
                operationTypeCodes.getCheque(),
                operationTypeCodes.getEffect(),
                operationTypeCodes.getDirectDebit(),
                operationTypeCodes.getTransfer(),
                operationTypeCodes.getPMobile(),

                hasRight(rightIds, 61) // Recherche par autres agences
        );
    }

    /*
     * Returns the currently authenticated application user.
     */
    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            throw new IllegalStateException(
                    "No authenticated user found."
            );
        }

        String username = authentication.getName();

        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new IllegalStateException(
                    "Authenticated user not found: " + username
            );
        }

        return user;
    }

    /*
     * Collects active right IDs from all roles assigned to the user.
     *
     * Right is considered active when:
     * - status = 1
     * - value = "true"
     *
     * This matches the rights data you provided.
     */
    private Set<Integer> getActiveRightIds(User user) {

        if (user.getAuthorites() == null) {
            return Set.of();
        }

        return user.getAuthorites()
                .stream()
                .filter(Role.class::isInstance)
                .map(Role.class::cast)
                .filter(role -> role.getRights() != null)
                .flatMap(role -> role.getRights().stream())
                .filter(this::isRightActive)
                .map(Right::getId)
                .filter(Objects::nonNull)
                .map(Math::toIntExact)
                .collect(Collectors.toSet());
    }

    private boolean isRightActive(Right right) {

        return right.getId() != null
                && Integer.valueOf(1).equals(right.getStatus())
                && Boolean.parseBoolean(right.getValue());
    }

    private boolean hasRight(Set<Integer> rightIds, int rightId) {
        return rightIds.contains(rightId);
    }

    /*
     * Do not allow a search to execute with missing operation type codes.
     * The legacy query uses these codes to filter OPERATIONS.typeoperation.
     */
    private void validateOperationTypeCodes(Set<Integer> rightIds) {

        if (hasRight(rightIds, 51)) {
            requireCode("cardPayment", operationTypeCodes.getCardPayment());
            requireCode("cardWithdrawal", operationTypeCodes.getCardWithdrawal());
        }

        if (hasRight(rightIds, 56)) {
            requireCode("cheque", operationTypeCodes.getCheque());
        }

        if (hasRight(rightIds, 62)) {
            requireCode("effect", operationTypeCodes.getEffect());
        }

        if (hasRight(rightIds, 67)) {
            requireCode("directDebit", operationTypeCodes.getDirectDebit());
        }

        if (hasRight(rightIds, 72)) {
            requireCode("transfer", operationTypeCodes.getTransfer());
        }

        if (hasRight(rightIds, 78)) {
            requireCode("pMobile", operationTypeCodes.getPMobile());
        }
    }

    private void requireCode(String propertyName, String value) {

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing operation type configuration: "
                            + "webaccess.operation-types."
                            + propertyName
            );
        }
    }
}