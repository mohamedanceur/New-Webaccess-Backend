package com.Atos.Webaccess.Domain.Virement;

import java.util.List;

import com.Atos.Webaccess.Domain.Virement.DTO.VirementRequest;
import com.Atos.Webaccess.Domain.Virement.DTO.VirementResponse;
import com.Atos.Webaccess.User.Entities.User;

public interface VirementService {

    VirementResponse addVirementCli(
            VirementRequest request,
            User user,
            String host
    );

    VirementResponse addVirementBab(
            VirementRequest request,
            User user,
            String host
    );

    VirementResponse addVirementEtr(
            VirementRequest request,
            User user,
            String host
    );

    List<VirementResponse> getPendingVirements();

    int validateVirements(List<Integer> pendingIds, User user, String host);

    int deleteVirement(Integer virementId, Integer pendingOperationId);
}