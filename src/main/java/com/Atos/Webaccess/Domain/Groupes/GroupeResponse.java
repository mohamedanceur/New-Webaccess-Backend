package com.Atos.Webaccess.Domain.Groupes;


import java.util.Set;

public record GroupeResponse(
    Integer id,
    String libelle,
    String libelleAgence,
    Integer statut,
    Set<Integer> agenceIds
) {}
