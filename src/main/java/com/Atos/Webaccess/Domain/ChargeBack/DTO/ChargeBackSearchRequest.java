package com.Atos.Webaccess.Domain.ChargeBack.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeBackSearchRequest {

    private String date_du;
    private String date_au;
    private String typeDate = "datesaisie";

    private String banque = "-1";
    private String agence = "-1";
    private String quelagence = "-1";

    private String mtn_min = "";
    private String mtn_max = "";

    private String compte = "";
    private String rio = "";
    private String iio = "";

    private String origine = "-1";
    private String stat_comp = "-1";
    private String etat = "-1";

    private String nom_tire = "";
    private String nom_ben = "";
    private String motif_rej = "";

    private String type_opr_carte = "-1";
    private String n_carte = "";
    private String bin = "";
    private String n_aut = "";
}