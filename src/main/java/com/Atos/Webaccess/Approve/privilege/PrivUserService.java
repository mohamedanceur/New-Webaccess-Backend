package com.Atos.Webaccess.Approve.privilege;

import com.Atos.Webaccess.Approve.service.CurrentUserProvider;
import com.Atos.Webaccess.Domain.Tache.Tache;
import com.Atos.Webaccess.Domain.Tache.TacheRepository;
import com.Atos.Webaccess.User.Entities.User;
import com.Atos.Webaccess.User.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivUserService {

    private static final long RIGHT_TASK_PRIVILEGES = 99L;

    public record UserItem(long id, String username, String nom, String prenom) {}

    private final CurrentUserProvider currentUserProvider;
    private final UserRepository userRepository;
    private final TacheRepository tacheRepository;

    /** Legacy getUsersByBanque: active users of the caller's bank, ordered by pseudo. */
    @Transactional(readOnly = true)
    public List<UserItem> users() {
        User caller = authorizedCaller();
        return userRepository.findByIdBanqueAndStatusOrderByUsernameAsc(caller.getIdBanque(), 1).stream()
                .map(u -> new UserItem(u.getId(), u.getUsername(), u.getNom(), u.getPrenom()))
                .toList();
    }

    /** Legacy getIdTachesUser: ids of the user's ACTIVE tasks. */
    @Transactional(readOnly = true)
    public List<Integer> taskIds(Long userId) {
        User target = targetUser(authorizedCaller(), userId);
        return target.getTaches_user().stream()
                .filter(t -> Integer.valueOf(1).equals(t.getStatut()))
                .map(Tache::getId)
                .sorted()
                .toList();
    }

    /**
     * Legacy updatePrivTache: the user's task set becomes exactly the checked ACTIVE tasks.
     * Tache owns the join table, so the change is written through Tache.usersTache.
     */
    @Transactional
    public void replaceTasks(Long userId, Set<Integer> checkedIds) {
        User target = targetUser(authorizedCaller(), userId);

        List<Tache> activeTasks = tacheRepository.findByStatutOrderByLibelleAsc(1);
        Set<Integer> wanted = activeTasks.stream()
                .map(Tache::getId)
                .filter(checkedIds::contains)
                .collect(Collectors.toSet());

        Set<Tache> current = new HashSet<>(target.getTaches_user());

        current.stream()
                .filter(t -> !wanted.contains(t.getId()))
                .forEach(t -> t.getUsersTache().remove(target));

        activeTasks.stream()
                .filter(t -> wanted.contains(t.getId()) && !current.contains(t))
                .forEach(t -> t.getUsersTache().add(target));
    }

    // ---- helpers -----------------------------------------------------------

    private User authorizedCaller() {
        User caller = currentUserProvider.authenticatedUser();
        if (!currentUserProvider.activeRightIds(caller).contains(RIGHT_TASK_PRIVILEGES)) {
            throw new AccessDeniedException("Task privileges administration is not permitted.");
        }
        return caller;
    }

    /** Improvement over legacy: the target user must belong to the caller's bank. */
    private User targetUser(User caller, Long userId) {
        User target = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown user: " + userId));
        if (!Objects.equals(target.getIdBanque(), caller.getIdBanque())) {
            throw new AccessDeniedException("User belongs to another bank.");
        }
        return target;
    }
}