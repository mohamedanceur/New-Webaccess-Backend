package com.Atos.Webaccess.Approve.transition;

import com.Atos.Webaccess.Approve.privilege.PrivUserService;
import com.Atos.Webaccess.Approve.recherche.ApproveSearchService;
import com.Atos.Webaccess.Domain.Operations.OperationRepository;
import com.Atos.Webaccess.Domain.Operations.OperationTypeCodeProperties;
import com.Atos.Webaccess.Domain.Tache.Tache;
import com.Atos.Webaccess.Domain.Tache.TacheRepository;
import com.Atos.Webaccess.User.Entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import java.util.Date;
import java.util.*;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import com.Atos.Webaccess.Domain.Agence.AgenceRepository;
import com.Atos.Webaccess.Domain.Evenement.EvenementService;
import com.Atos.Webaccess.Domain.Libelle.LibelleRepository;
import com.Atos.Webaccess.Approve.service.CurrentUserProvider;
import com.Atos.Webaccess.User.Repositories.UserRepository;


@ExtendWith(MockitoExtension.class)
class PrivUserServiceTest {

    @Mock CurrentUserProvider currentUserProvider;
    @Mock UserRepository userRepository;
    @Mock TacheRepository tacheRepository;
    @InjectMocks PrivUserService service;

    private Tache task(int id) {
        Tache t = new Tache();
        t.setId(id);
        t.setStatut(1);
        return t;
    }

    private User userOfBank(long id, long bank) {
        User u = new User();
        u.setId(id);
        u.setIdBanque(bank);
        return u;
    }

    @Test
    void replacesTheTaskSetThroughTheOwningSide() {
        User caller = userOfBank(1, 7);
        User target = userOfBank(5, 7);
        Tache t9 = task(9), t10 = task(10), t12 = task(12);
        t10.getUsersTache().add(target);                       // linked now, will be unchecked
        target.setTaches_user(new HashSet<>(Set.of(t10)));

        when(currentUserProvider.authenticatedUser()).thenReturn(caller);
        when(currentUserProvider.activeRightIds(caller)).thenReturn(Set.of(99L));
        when(userRepository.findById(5L)).thenReturn(Optional.of(target));
        when(tacheRepository.findByStatutOrderByLibelleAsc(1)).thenReturn(List.of(t9, t10, t12));

        service.replaceTasks(5L, Set.of(9, 12));

        assertTrue(t9.getUsersTache().contains(target));
        assertTrue(t12.getUsersTache().contains(target));
        assertFalse(t10.getUsersTache().contains(target));
    }

    @Test
    void refusesAUserOfAnotherBank() {
        User caller = userOfBank(1, 7);
        when(currentUserProvider.authenticatedUser()).thenReturn(caller);
        when(currentUserProvider.activeRightIds(caller)).thenReturn(Set.of(99L));
        when(userRepository.findById(5L)).thenReturn(Optional.of(userOfBank(5, 8)));

        assertThrows(AccessDeniedException.class, () -> service.replaceTasks(5L, Set.of(9)));
    }
}
