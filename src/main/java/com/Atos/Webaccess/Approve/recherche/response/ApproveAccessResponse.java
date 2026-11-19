package com.Atos.Webaccess.Approve.recherche.response;


import com.Atos.Webaccess.Domain.Libelle.Libelle;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ApproveAccessResponse {

    private String target;

    private List<Libelle> states;

    private List<Libelle> rejectionStates;

    private List<Libelle> accountStates;

    private boolean canValidateAllOperations;
}
