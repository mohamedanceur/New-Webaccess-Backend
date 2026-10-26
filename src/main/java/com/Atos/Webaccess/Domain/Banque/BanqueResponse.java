package com.Atos.Webaccess.Domain.Banque;


public record BanqueResponse(
    Integer id,
    String code,
    Integer idPays,
    String libelle,
    Integer codeBin,
    Integer algo,
    Integer banqueMere,
    Integer statut,
    java.util.Date datemaj
) {}
