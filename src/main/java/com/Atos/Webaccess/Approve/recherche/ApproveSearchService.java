package com.Atos.Webaccess.Approve.recherche;

import com.Atos.Webaccess.Approve.ApproveOperationDto;
import com.Atos.Webaccess.Domain.Agence.Agence;
import com.Atos.Webaccess.Domain.Agence.AgenceRepository;
import com.Atos.Webaccess.Domain.Banque.Banque;
import com.Atos.Webaccess.Domain.Operations.DTO.ApproveSearchRequest;
import com.Atos.Webaccess.Domain.Operations.Operation;
import com.Atos.Webaccess.Domain.Operations.OperationTypeCodeProperties;
import com.Atos.Webaccess.Domain.cheques.Cheque;
import com.Atos.Webaccess.Domain.effets.Effet;
import com.Atos.Webaccess.User.Entities.Right;
import com.Atos.Webaccess.User.Entities.Role;
import com.Atos.Webaccess.User.Entities.User;
import com.Atos.Webaccess.User.Repositories.UserRepository;
import com.Atos.Webaccess.config.ApplicationParameters;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApproveSearchService {

    private static final long RIGHT_APPROVE_OPERATIONS = 97L;
    private static final long RIGHT_APPROVE_TASK_PRIVILEGES = 99L;

    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final AgenceRepository agenceRepository;
    private final OperationTypeCodeProperties operationTypeCodes;

    private final ApplicationParameters applicationParameters;
    public ApproveSearchResponse search(ApproveSearchRequest request) {
        validateRequest(request);

        User user = authenticatedUser();
        Set<Long> rights = activeRightIds(user);

        
        if (!rights.contains(RIGHT_APPROVE_OPERATIONS) && !rights.contains(RIGHT_APPROVE_TASK_PRIVILEGES)) {
            throw new AccessDeniedException("APPROVE operation search is not permitted.");
        }

        String direction = request.getSens().trim().toUpperCase();
        int instrument = parseInstrument(request);
        boolean cheque = instrument == 3;
        boolean effet = instrument == 6;

        if (!cheque && !effet) {
            throw new IllegalArgumentException(
                    "This APPROVE search endpoint currently supports only Cheque (3) and Effet (6).");
        }

        String state = effectiveState(request);
        int stateCode = parseState(state);
        boolean task105 = stateCode == 105;

        String bankCode = user.getBank() == null ? null : user.getBank().getCode();
        if (bankCode == null || bankCode.isBlank()) {
            throw new IllegalStateException("Unable to resolve the authenticated user's bank code.");
        }

        String configuredAgency = applicationParameters.getCodeAgence();
        if (configuredAgency == null || configuredAgency.isBlank()) {
            configuredAgency = "*";
        }
        
        List<String> groupAgencyCodes = agenceRepository
                .findAgencesByGroupeId(Math.toIntExact(user.getIdGroupe()))
                .stream()
                .map(Agence::getCode)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<String> allAgencyCodes = agenceRepository.findAll().stream()
                .map(Agence::getCode)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        Date cutoff = Date.from(
                LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault())
                        .minusSeconds(1).toInstant());

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Operation> op = cq.from(Operation.class);

        List<Predicate> predicates = new ArrayList<>();

        if (cheque) {
            Root<Cheque> ch = cq.from(Cheque.class);
            predicates.add(cb.equal(op.get("idinstrument"), ch.get("idcheque")));

            String chequeCode = operationTypeCodes.getCheque();
            if (chequeCode == null || chequeCode.isBlank()) {
                throw new IllegalStateException(
                        "Missing webaccess.operation-types.cheque configuration.");
            }
            predicates.add(cb.like(op.get("typeoperation"), "0" + chequeCode + "%"));

            cq.multiselect(
                    op,
                    ch.get("numerocheque").alias("instrumentNumber"),
                    ch.get("fichierimage").alias("fileName"),
                    ch.get("pathimage").alias("imagePath"),
                    cb.nullLiteral(String.class).alias("bankLabel"),
                    cb.nullLiteral(String.class).alias("avalCode")
            );

            if ("R".equals(direction)) {
                Root<Banque> bank = cq.from(Banque.class);
                predicates.add(cb.equal(
                        bank.get("code"),
                        cb.substring(op.<String>get("ribbeneficiaire"), 1, bankCode.length())));
                cq.multiselect(
                        op,
                        ch.get("numerocheque").alias("instrumentNumber"),
                        ch.get("fichierimage").alias("fileName"),
                        ch.get("pathimage").alias("imagePath"),
                        bank.get("libelle").alias("bankLabel"),
                        cb.nullLiteral(String.class).alias("avalCode")
                );
            }
        } else {
            Root<Effet> effect = cq.from(Effet.class);
            Root<Banque> bank = cq.from(Banque.class);
            predicates.add(cb.equal(op.get("idinstrument"), effect.get("ideffet")));

            String bankSource = "A".equals(direction)
                    ? "agenceremettante"
                    : "ribbeneficiaire";

            predicates.add(cb.equal(
                    bank.get("code"),
                    cb.substring(op.<String>get(bankSource), 1, bankCode.length())));

            cq.multiselect(
                    op,
                    effect.get("numeroeffet").alias("instrumentNumber"),
                    effect.get("fichierimage").alias("fileName"),
                    effect.get("pathimage").alias("imagePath"),
                    bank.get("libelle").alias("bankLabel"),
                    effect.get("codeaval").alias("avalCode")
            );
        }

        // Legacy behavior: task state 105 is always treated as Aller.
        if (task105 || "A".equals(direction)) {
            predicates.add(cb.lessThan(op.get("origine"), 4));
        } else {
            predicates.add(cb.greaterThan(op.get("origine"), 3));
        }

        predicates.add(cb.equal(op.get("etat"), stateCode));

        // Legacy agency restriction.
        if (!"*".equals(configuredAgency)) {
            if ("A".equals(direction)) {
                predicates.add(cb.equal(op.get("agencebeneficiaire"), configuredAgency));
            } else if (rights.contains(RIGHT_APPROVE_TASK_PRIVILEGES)) {
                // Right 99 is the new APPROVE task-privilege right:
                // reproduce legacy NOT IN (all agencies outside the user's group).
                Set<String> group = new HashSet<>(groupAgencyCodes);
                List<String> excluded = allAgencyCodes.stream()
                        .filter(code -> !group.contains(code))
                        .toList();
                if (!excluded.isEmpty()) {
                    predicates.add(op.get("agencetire").in(excluded).not());
                }
            } else if (!groupAgencyCodes.isEmpty()) {
                predicates.add(op.get("agencetire").in(groupAgencyCodes));
            } else {
                predicates.add(cb.disjunction());
            }
        }

        String beneficiaryBank = request.getApproveBeneficiaryBank();
        if (beneficiaryBank != null && !beneficiaryBank.isBlank()
                && !"-1".equals(beneficiaryBank)) {
            predicates.add(cb.equal(op.get("banquebeneficiaire"), beneficiaryBank));
        }

        // In the legacy configuration, '*' disables the establishment filter.
        String establishment = applicationParameters.getCodeBanque();
        if (establishment != null && !establishment.isBlank() && !"*".equals(establishment)) {
            predicates.add(cb.equal(op.get("etablissement"), establishment));
        }

        // Both legacy Cheque and Effet searches use DATEREGLEMENT > today 23:59:59.
        predicates.add(cb.greaterThan(op.<Date>get("datereglement"), cutoff));

        cq.where(cb.and(predicates.toArray(Predicate[]::new)));
        cq.orderBy(cb.asc(op.get("ribtire")));

        TypedQuery<Tuple> query = entityManager.createQuery(cq);
        List<ApproveOperationDto> rows = query.getResultList().stream()
                .map(tuple -> mapResult(
                        tuple,
                        cheque ? 3 : 6,
                        stateCode))
                .toList();

        return ApproveSearchResponse.builder()
                .instrument(cheque ? "CHEQUE" : "EFFET")
                .sens(direction)
                .total(rows.size())
                .operations(rows)
                .build();
    }

    private ApproveOperationDto mapResult(Tuple tuple, int instrument, int selectedState) {
        Operation op = tuple.get(0, Operation.class);

        String reason = op.getMotifrejet();
        if (reason != null && reason.length() == 5) {
            reason = reason.substring(2, 5);
        }

        String compensationStatus = "";
        if ("O".equalsIgnoreCase(op.getIndicateurrejet())) {
            compensationStatus = "Rejeté";
        } else if ("O".equalsIgnoreCase(op.getIndicateurannulation())) {
            compensationStatus = "Annulé";
        } else if ("O".equalsIgnoreCase(op.getIndicateurrepresentation())) {
            compensationStatus = "Représenté";
        }

        String actionRib = (op.getOrigine() != null && op.getOrigine() > 3)
                || selectedState == 105 ? op.getRibtire() : op.getRibbeneficiaire();

        return ApproveOperationDto.builder()
                .idoperation(op.getIdoperation())
                .ribtire(op.getRibtire())
                .ribbeneficiaire(op.getRibbeneficiaire())
                .montantoperation(parseCents(op.getMontantoperation()))
                .devise(op.getDevise())
                .motifrejet(reason)
                .rio(op.getRio())
                .referenceinterne(op.getReferenceinterne())
                .etat(op.getEtat())
                .origine(op.getOrigine())
                .indicateurrejet(op.getIndicateurrejet())
                .indicateurannulation(op.getIndicateurannulation())
                .indicateurrepresentation(op.getIndicateurrepresentation())
                .datereglement(op.getDatereglement())
                .numeroInstrument(tuple.get("instrumentNumber", String.class))
                .fichierimage(tuple.get("fileName", String.class))
                .pathimage(tuple.get("imagePath", String.class))
                .libBanque(tuple.get("bankLabel", String.class))
                .codeAval(tuple.get("avalCode", String.class))
                .statComp(compensationStatus)
                .ribAction(actionRib)
                .instrument(instrument)
                .build();
    }

    private BigDecimal parseCents(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return new BigDecimal(value.trim()).movePointLeft(2);
        } catch (NumberFormatException ex) {
            throw new IllegalStateException(
                    "Invalid MONTANTOPERATION value in OPERATIONS.", ex);
        }
    }

    private User authenticatedUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

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

    private Set<Long> activeRightIds(User user) {
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
                .collect(java.util.stream.Collectors.toSet());
    }

    private boolean isActive(Right right) {
        return Integer.valueOf(1).equals(right.getStatus())
                && Boolean.parseBoolean(right.getValue());
    }

    private void validateRequest(ApproveSearchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("APPROVE search request is required.");
        }
        String direction = request.getSens();
        if (direction == null || (!"A".equalsIgnoreCase(direction)
                && !"R".equalsIgnoreCase(direction))) {
            throw new IllegalArgumentException("APPROVE direction must be A or R.");
        }
    }

    private int parseInstrument(ApproveSearchRequest request) {
        Integer instrument = request.getInstrument();

        if (instrument == null) {
            throw new IllegalArgumentException(
                    "APPROVE instrument is required (3 = Cheque, 6 = Effet)."
            );
        }

        if (instrument != 3 && instrument != 6) {
            throw new IllegalArgumentException(
                    "Unsupported APPROVE instrument. Only 3 (Cheque) and 6 (Effet) are supported."
            );
        }

        return instrument;
    }

    private String effectiveState(ApproveSearchRequest request) {
        String state = request.getEtatSelectTache();

        return state == null || state.isBlank() ? "-1" : state.trim();
    }

    private int parseState(String state) {
        try {
            return Integer.parseInt(state);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid APPROVE task state: " + state, ex);
        }
    }
}
