package com.Atos.Webaccess.Domain.Prelevement.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrelevementRequest {

    private Integer idprelevement;
    private Integer idoperation;

    private String dateexecution;
    private String datefacture;
    private String dateremise;
    private String donneesemetteur;
    private String numeroemetteur;
    private String libelle;

    private String montant;

    private String banquebeneficiaire;
    private String agencebeneficiaire;
    private String numcomptebeneficiaire;
    private String clebeneficiaire;
    private String nombeneficiaire;
    private String adressebeneficiaire;
    private String ibanbeneficiaire;

    private String banquetire;
    private String agencetire;
    private String numcomptetire;
    private String cletire;
    private String nomtire;
    private String adressetire;
    private String ibantire;

    private String codeplace;
    private String rio;

    private Integer idinstrument;
    private Integer idlotinti;
    private Integer idlotinto;
    private Integer idlotuap;
    private Integer idoperationrejet;
    private Integer idfichierimgin;
    private Integer idfichierimgout;
    private Integer idimageuap;
    private Integer idremise;
    private Integer idfichiercra;

    private String datepresentation;
    private String datereglement;
}