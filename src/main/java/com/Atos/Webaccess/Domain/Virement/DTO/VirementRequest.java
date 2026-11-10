package com.Atos.Webaccess.Domain.Virement.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VirementRequest {

    private Integer idvirement;
    private Integer idoperation;

    // Transfer type and indicators
    private String typVirement;
    private String indicateurT = "1";
    private String indicateurB = "1";

    // Funds
    private String natFond;
    private String montant;
    private String libelle;

    // Ordering customer
    private String codeAgenceTire;
    private String numCompteTire;
    private String cleTire;
    private String ribTire;
    private String nomPrenTire;
    private String adrTire;
    private String agenceRemettante;

    // Beneficiary
    private String codeAgenceBene;
    private String banquebeneficiaire;
    private String numCompteBene;
    private String cleBene;
    private String ribBene;
    private String nomPrenBene;
    private String adrBene;

    // Internal references
    private String numInterne;
    private String codeAgenceInitOrdre;
    private String dateReception;
    private String refInit;

    // Initial currency and fees (international transfer)
    private String montantInitial;
    private String deviseInitial;
    private String deviseInitialAutre;

    private String montantFrais1;
    private String deviseFrais1;
    private String deviseFrais1Autre;

    private String montantFrais2;
    private String deviseFrais2;
    private String deviseFrais2Autre;

    private String montantFrais3;
    private String deviseFrais3;
    private String deviseFrais3Autre;

    private String montantFrais4;
    private String deviseFrais4;
    private String deviseFrais4Autre;
}