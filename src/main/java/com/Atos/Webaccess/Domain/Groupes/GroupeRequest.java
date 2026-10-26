package com.Atos.Webaccess.Domain.Groupes;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record GroupeRequest(
    @NotBlank
    @Size(max = 255)
    String libelle,

    String libelleAgence,

    @NotNull
    Set<Integer> agenceIds
) {}
