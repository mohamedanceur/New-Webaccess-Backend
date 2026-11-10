package com.Atos.Webaccess.Domain.Operations.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationSearchRequest {

    private String date_du;
    private String date_au;

    // datesaisie, datepresentation, datetraitement,
    // datereglement, dateescompte
    private String typeDate = "datesaisie";

    private String agence = "-1";
    private String banque = "-1";
    private String quelagence = "-1";

    private String mtn_min;
    private String mtn_max;

    private String compte;
    private String rio;
    private String iio;

    // Aller / Retour / -1
    private String origine = "-1";

    // 1 = Annulation, 2 = Rejet, 3 = Représentation
    private String stat_comp = "-1";

    private String etat = "-1";

    private String nom_tire;
    private String nom_ben;
    private String motif_rej;
}