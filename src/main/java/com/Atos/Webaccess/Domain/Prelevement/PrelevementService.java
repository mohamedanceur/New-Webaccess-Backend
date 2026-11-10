package com.Atos.Webaccess.Domain.Prelevement;

import com.Atos.Webaccess.Domain.Prelevement.DTO.PrelevementRequest;
import com.Atos.Webaccess.Domain.Prelevement.DTO.PrelevementResponse;

import java.util.List;

public interface PrelevementService {

    // List pending direct debits
    List<PrelevementResponse> getPendingPrelevements(
            Integer etat,
            Integer origine,
            String typeOperation,
            Integer idGroupe
    );

    // Add a direct debit
    void addPrelevement(
            PrelevementRequest request,
            String typeOperation,
            String devise,
            String reference,
            String username,
            String host,
            String motifRejet
    );

    // Modify a pending direct debit
    void updatePrelevement(
            PrelevementRequest request,
            String typeOperation,
            String devise,
            String reference,
            String username,
            String host,
            String motifRejet
    );

    // Validate several pending operations
    void validatePrelevements(
            List<Integer> operationIds,
            String username,
            String host
    );

    // Delete a direct debit
    void deletePrelevement(Integer idPrelevement, Integer idOperation);

    // Retrieve operation details
    PrelevementResponse getPrelevementDetails(Integer idOperation);
}