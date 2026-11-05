package com.Atos.Webaccess.Domain.Evenement;


import java.util.Optional;

public interface EvenementService {

    Evenement save(Evenement evenement);

    Optional<Evenement> findById(Integer id);

    Evenement addEvenement_CRE(
            Integer idop,
            String etablis,
            String name,
            String host,
            String etatInitial,
            String etatFinal
    ) throws Exception;

    Evenement addEvenement_INT_APPROVE(
            Integer idop,
            String name,
            String host,
            int etatInitial,
            int etatFinal,
            String idUser,
            int taillecodebanque,
            int taillecodeagence,
            String codeAgence
    ) throws Exception;

    Evenement addEvenement_upadate(
            Integer idop,
            String name,
            String host,
            int etatInitial,
            int etatFinal,
            String idUser,
            int taillecodebanque,
            int taillecodeagence,
            String codeAgence
    ) throws Exception;
}