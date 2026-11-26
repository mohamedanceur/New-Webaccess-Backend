package com.Atos.Webaccess.Approve.transition;

import com.Atos.Webaccess.Approve.recherche.ApproveOperationDto;
import com.Atos.Webaccess.Approve.recherche.ApproveSearchService;
import com.Atos.Webaccess.Approve.service.CurrentUserProvider;
import com.Atos.Webaccess.Domain.Agence.Agence;
import com.Atos.Webaccess.Domain.Agence.AgenceRepository;
import com.Atos.Webaccess.Domain.Evenement.EvenementService;
import com.Atos.Webaccess.Domain.Libelle.Libelle;
import com.Atos.Webaccess.Domain.Libelle.LibelleRepository;
import com.Atos.Webaccess.Domain.Operations.OperationRepository;
import com.Atos.Webaccess.Domain.Operations.OperationTypeCodeProperties;
import com.Atos.Webaccess.Domain.Operations.DTO.ApproveSearchRequest;
import com.Atos.Webaccess.Domain.Tache.Tache;
import com.Atos.Webaccess.Domain.Tache.TacheRepository;
import com.Atos.Webaccess.User.Entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ApproveTransitionService {

    private static final long RIGHT_APPROVE_OPERATIONS = 97L;
    private static final long RIGHT_APPROVE_TASK_PRIVILEGES = 99L;
    private static final String ALL = "*";
    private static final String REJECT_PREFIX = "MC";

    private static final long RIGHT_VALIDATE_ALL = 98L;
    private static final int DEFAULT_VALIDATE_ALL_STATE = 115;
    private static final int IN_LIST_CHUNK = 500;

    private final ApproveSearchService approveSearchService;
    private final TacheRepository tacheRepository;
    
    private final CurrentUserProvider currentUserProvider;
    private final OperationRepository operationRepository;
    private final EvenementService evenementService;
    private final LibelleRepository libelleRepository;
    private final AgenceRepository agenceRepository;
    private final OperationTypeCodeProperties operationTypeCodes;

    @Transactional
    public TransitionResult transition(Integer operationId, TransitionRequest request, String clientHost) {
        User user = currentUserProvider.authenticatedUser();
        requireApproveRight(user);

        Tache task = findAssignedTask(user, request.sens(), request.instrument(), request.fromState());
        if (!basculeStates(task).contains(request.toState())) {
            throw new IllegalArgumentException(
                    "State " + request.toState() + " is not allowed for task " + task.getLibelle());
        }

        Date now = new Date();
        int updated;
        if (rejectionStates().contains(request.toState())) {
            String motif = validMotif(request.motifRejet());
            updated = operationRepository.transitionWithReject(
                    operationId, request.toState(), now, request.fromState(), REJECT_PREFIX + motif);
        } else {
            updated = operationRepository.transition(
                    operationId, request.toState(), now, request.fromState());
        }

        if (updated > 0) {
        	saveEvent(operationId, user, clientHost, request.fromState(), request.toState());        }
        return new TransitionResult(updated);
    }

    // ---- task checks -------------------------------------------------------

    private Tache findAssignedTask(User user, String sens, int instrument, int fromState) {
        String prefix = "0" + instrumentCode(instrument);
        String from = String.valueOf(fromState);

        return user.getTaches_user().stream()
                .filter(t -> Integer.valueOf(1).equals(t.getStatut()))
                .filter(t -> t.getType() != null
                        && (ALL.equals(t.getType().trim()) || sens.trim().equalsIgnoreCase(t.getType().trim())))
                .filter(t -> matchesInstrument(t, prefix))
                .filter(t -> t.getEtatselect() != null && from.equals(t.getEtatselect().trim()))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("No task assigned to the user for this state."));
    }

    private boolean matchesInstrument(Tache t, String prefix) {
        return t.getTypeins() != null
                && (ALL.equals(t.getTypeins().trim())
                    || t.getTypeins().trim().matches(Pattern.quote(prefix) + "."));
    }

    private Set<Integer> basculeStates(Tache t) {
        return Stream.of(t.getEtatbascule1(), t.getEtatbascule2(), t.getEtatbascule3(),
                        t.getEtatbascule4(), t.getEtatbascule5(), t.getEtatbascule6(),
                        t.getEtatbascule7(), t.getEtatbascule8(), t.getEtatbascule9(),
                        t.getEtatbascule10())
                .filter(s -> s != null && !s.isBlank())
                .map(s -> Integer.valueOf(s.trim()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Integer> rejectionStates() {
        return libelleRepository.findByTypeAndLibelle3("ETAT", "Rejet").stream()
                .map(Libelle::getCode)
                .filter(Objects::nonNull)
                .map(c -> Integer.valueOf(c.trim()))
                .collect(Collectors.toSet());
    }

    private String validMotif(String motif) {
        if (motif == null || motif.isBlank()) {
            throw new IllegalArgumentException("A rejection reason is required for this state.");
        }
        boolean known = libelleRepository.findByTypeOrderByCodeAsc("CODE_ERREUR").stream()
                .anyMatch(l -> motif.trim().equals(l.getCode()));
        if (!known) {
            throw new IllegalArgumentException("Unknown rejection reason: " + motif);
        }
        return motif.trim();
    }

    // ---- helpers -----------------------------------------------------------

    private void requireApproveRight(User user) {
        Set<Long> rights = currentUserProvider.activeRightIds(user);
        if (!rights.contains(RIGHT_APPROVE_OPERATIONS) && !rights.contains(RIGHT_APPROVE_TASK_PRIVILEGES)) {
            throw new AccessDeniedException("APPROVE operation processing is not permitted.");
        }
    }

    private String instrumentCode(int instrument) {
        String code = switch (instrument) {
            case 3 -> operationTypeCodes.getCheque();
            case 6 -> operationTypeCodes.getEffect();
            default -> throw new IllegalArgumentException("Unsupported instrument: " + instrument);
        };
        if (code == null || code.isBlank()) {
            throw new IllegalStateException("Operation type code is not configured for instrument " + instrument);
        }
        return code;
    }

    private void saveEvent(Integer operationId, User user, String clientHost, int fromState, int toState) {
        try {
            evenementService.save(evenementService.addEvenement_INT_APPROVE(
                    operationId, user.getNom(), clientHost, fromState, toState,
                    String.valueOf(user.getId()), 0, 0, eventAgencyCode(user)));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to record the approval event.", e);
        }
    }

    /** Legacy used the first agency of the user's group. */
    private String eventAgencyCode(User user) {
        return agenceRepository.findAgencesByGroupeId(Math.toIntExact(user.getIdGroupe())).stream()
                .map(Agence::getCode)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(user.getDefault_Code_Agence());
    }
    
    @Transactional
    public ValidateAllResult validateAll(ApproveSearchRequest criteria, String clientHost) {
        User user = currentUserProvider.authenticatedUser();
        if (!currentUserProvider.activeRightIds(user).contains(RIGHT_VALIDATE_ALL)) {
            throw new AccessDeniedException("Validating all operations is not permitted.");
        }

        String state = criteria.getEtatSelectTache();
        if (state == null || state.isBlank() || "-1".equals(state.trim())) {
            throw new IllegalArgumentException("A task state must be selected.");
        }
        int fromState = Integer.parseInt(state.trim());
        int instrument = criteria.getInstrument();

        findAssignedTask(user, criteria.getSens(), instrument, fromState);  // user must own this task
        int toState = validateAllTargetState(instrument);

        List<Integer> ids = approveSearchService.search(criteria).getOperations().stream()
                .map(ApproveOperationDto::getIdoperation)
                .toList();
        if (ids.isEmpty()) {
            return new ValidateAllResult(0, 0, toState);
        }

        Date now = new Date();
        int updated = 0;
        for (int i = 0; i < ids.size(); i += IN_LIST_CHUNK) {
            List<Integer> chunk = ids.subList(i, Math.min(i + IN_LIST_CHUNK, ids.size()));
            updated += operationRepository.transitionAll(chunk, toState, now, fromState);
        }

        if (updated > 0) {
            ids.forEach(id -> saveEvent(id, user, clientHost, fromState, toState));
        }
        return new ValidateAllResult(ids.size(), updated, toState);
    }

    /** Legacy: first active task of type 'V' for the instrument, its etatbascule1, else 115. */
    private int validateAllTargetState(int instrument) {
        String prefix = "0" + instrumentCode(instrument);
        return tacheRepository.findByTypeAndStatutOrderByEtatselectAsc("V", 1).stream()
                .filter(t -> matchesInstrument(t, prefix))
                .map(Tache::getEtatbascule1)
                .filter(s -> s != null && !s.isBlank())
                .map(s -> Integer.valueOf(s.trim()))
                .findFirst()
                .orElse(DEFAULT_VALIDATE_ALL_STATE);
    }
}