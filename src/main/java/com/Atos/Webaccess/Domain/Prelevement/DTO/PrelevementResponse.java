package com.Atos.Webaccess.Domain.Prelevement.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PrelevementResponse {

    private Integer idoperation;
    private Integer idprelevement;
    private Integer idinstrument;

    private String etablissement;
    private String referenceinterne;
    private String typeoperation;
    private String libTypeoperation;

    private Integer etat;
    private String libEtat;

    private Integer origine;
    private String libOrigine;

    private String donneesemetteur;
    private String numeroemetteur;

    private String montantoperation;
    private String devise;

    private String banquetire;
    private String agencetire;
    private String numcomptetire;
    private String nomtire;
    private String adressetire;
    private String ribtire;
    private String ibantire;

    private String banquebeneficiaire;
    private String agencebeneficiaire;
    private String numcomptebeneficiaire;
    private String nombeneficiaire;
    private String adressebeneficiaire;
    private String ribbeneficiaire;
    private String ibanbeneficiaire;

    private String libelle;
    private String libelleoperation;

    private String dateexecution;
    private String datefacture;

    private Date datesaisie;
    private Date datetraitement;
    private Date datepresentation;
    private Date datereglement;

    private String motifrejet;
    private String libMotifrejet;

    private String rio;
    private String codeplace;

    private Integer idlotuap;
    private Integer idlotinti;
    private Integer idlotinto;
    private Integer idfichierimgin;
    private Integer idfichierimgout;
    private Integer idimageuap;
    private Integer idremise;
    private Integer idfichiercra;
    private Integer idoperationrejet;
    private String agenceremettante;
}