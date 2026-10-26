package com.Atos.Webaccess.Domain.parametres;



import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParametresService {

    private static final String FILTRE_INCIDENTS_PAIEMENT =
            "Filtre Incidents Paiement";

    private final ParametresRepository parametresRepository;

    public String getIncidentFilter() {
        List<String> valeurs =
                parametresRepository.findValeursByParametre(
                        FILTRE_INCIDENTS_PAIEMENT);

        String valeur = "";

        for (String item : valeurs) {
            valeur = item;
        }

        return valeur;
    }
}
