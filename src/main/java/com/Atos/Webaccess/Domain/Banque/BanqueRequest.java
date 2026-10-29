package com.Atos.Webaccess.Domain.Banque;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BanqueRequest(
    @NotBlank
    @Size(max = 100)
    String code,

    @NotNull
    Integer idPays,

    @NotBlank
    @Size(max = 255)
    String libelle,

    String codeBin,
    Integer algo,
    Integer banqueMere
) {}
