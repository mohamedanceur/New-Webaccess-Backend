package com.Atos.Webaccess.Domain.Carte;



import java.util.List;
import com.Atos.Webaccess.Domain.Carte.DTO.CarteRequest;
import com.Atos.Webaccess.Domain.Carte.DTO.CarteResponse;
import com.Atos.Webaccess.User.Entities.User;

public interface CarteService {

    List<CarteResponse> getPendingOperations();

    CarteResponse addCarteGAB(
            CarteRequest request,
            User user,
            String name,
            String host,
            String currency
    );

    CarteResponse addCarteTPE(
            CarteRequest request,
            User user,
            String name,
            String host,
            String currency
    );

    void validateOperations(
            List<Integer> operationIds,
            User user,
            String host
    );

    CarteRequest getCarteGABForModification(
            Integer idOperation,
            Integer idCarte,
            Integer bankCodeLength,
            Integer agencyCodeLength
    );

    CarteRequest getCarteTPEForModification(
            Integer idOperation,
            Integer idCarte,
            Integer finalAgencyCodeLength,
            Integer accountNumberLength,
            Integer ribKeyLength
    );

    CarteResponse modifyCarteGAB(
            Integer idOperation,
            CarteRequest request,
            User user,
            String currency
    );

    CarteResponse modifyCarteTPE(
            Integer idOperation,
            CarteRequest request,
            User user,
            String currency
    );
}