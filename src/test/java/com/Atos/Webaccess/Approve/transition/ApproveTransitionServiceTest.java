package com.Atos.Webaccess.Approve.transition;

import com.Atos.Webaccess.Approve.recherche.ApproveSearchService;
import com.Atos.Webaccess.Domain.Operations.DTO.ApproveSearchRequest;
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
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import com.Atos.Webaccess.Domain.Agence.AgenceRepository;
import com.Atos.Webaccess.Domain.Evenement.EvenementService;
import com.Atos.Webaccess.Domain.Libelle.LibelleRepository;
import com.Atos.Webaccess.Approve.service.CurrentUserProvider;
import com.Atos.Webaccess.Approve.recherche.ApproveSearchService;
import com.Atos.Webaccess.Domain.Operations.OperationRepository;
import com.Atos.Webaccess.Domain.Operations.OperationTypeCodeProperties;
import com.Atos.Webaccess.Domain.Tache.Tache;


@ExtendWith(MockitoExtension.class)
class ApproveTransitionServiceTest {

    @Mock CurrentUserProvider currentUserProvider;
    @Mock OperationRepository operationRepository;
    @Mock EvenementService evenementService;
    @Mock LibelleRepository libelleRepository;
    @Mock AgenceRepository agenceRepository;
    @Mock OperationTypeCodeProperties operationTypeCodes;
    @Mock ApproveSearchService approveSearchService;
    @Mock TacheRepository tacheRepository;
    @InjectMocks ApproveTransitionService service;

    private void givenUserWithTask(String etatselect, String bascule1) {
        Tache task = new Tache();
        task.setStatut(1);
        task.setType("A");
        task.setTypeins("03_");
        task.setLibelle("task");
        task.setEtatselect(etatselect);
        task.setEtatbascule1(bascule1);

        User user = new User();
        user.setIdGroupe(1L);               // adjust if the type differs
        user.setTaches_user(Set.of(task));

        when(currentUserProvider.authenticatedUser()).thenReturn(user);
        when(currentUserProvider.activeRightIds(user)).thenReturn(Set.of(97L));
        when(operationTypeCodes.getCheque()).thenReturn("3");
    }

    @Test
    void updatesOperationAndRecordsEvent() throws Exception {
        givenUserWithTask("1", "2");
        when(operationRepository.transition(eq(10), eq(2), any(Date.class), eq(1))).thenReturn(1);

        TransitionResult result = service.transition(10, new TransitionRequest("A", 3, 1, 2, null), "host");

        assertEquals(1, result.updated());
        verify(evenementService).save(any());
    }

    @Test
    void noEventWhenNothingWasUpdated() throws Exception {
        givenUserWithTask("1", "2");
        when(operationRepository.transition(eq(10), eq(2), any(Date.class), eq(1))).thenReturn(0);

        service.transition(10, new TransitionRequest("A", 3, 1, 2, null), "host");

        verify(evenementService, never()).save(any());
    }

    @Test
    void rejectsTargetStateNotAllowedByTask() {
        givenUserWithTask("1", "2");

        assertThrows(IllegalArgumentException.class,
                () -> service.transition(10, new TransitionRequest("A", 3, 1, 9, null), "host"));
        verifyNoInteractions(operationRepository);
    }

    @Test
    void rejectsWhenUserHasNoTaskForTheState() {
        givenUserWithTask("5", "2");

        assertThrows(AccessDeniedException.class,
                () -> service.transition(10, new TransitionRequest("A", 3, 1, 2, null), "host"));
    }
}
