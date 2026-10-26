package com.Atos.Webaccess.Domain.Agence;


import java.util.Date;

public record AgenceResponse(
    Integer id,
    String code,
    String code_pc,
    String libelle,
    Integer statut,
    Date datemaj,
    Integer id_banque,
    String libelleBanque
) {}
