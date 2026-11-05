package com.Atos.Webaccess.Domain.Carte.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarteRequest {

    // Identifiers - mainly used for modification
    private Integer idoperation;
    private Integer idopcarte;
    private String typeoperation;

    // Common card information
    private String numerocarte;
    private String numerotransaction;
    private String modelecturecarte;
    private String modeauthentification;
    private String numeroautorisation;
    private String datedebutvaliditeapplication;
    private String datefinvaliditeapplication;
    private String donneesemv;
    private String typecommission;

    // GAB
    private String montantretrait;
    private String idSystemRetrait;
    private String idPointRetrait;
    private String codeBanqueTire;
    private String codeAgenceTire;
    private String codeAgenceAcqBen;
    private String date_retrait;
    private String heureRetrait;
    private String montantOpComp;

    // TPE / Refund TPE
    private String typepaiement;
    private String telephonecommercant;
    private String numerocontrataccepteur;
    private String codeactivite;
    private String montantOpPaiement;
    private String sensoperation;
    private String montantComission;
    private String dateremise;
    private String idSystemAccept;
    private String idPointAccept;

    // TPE beneficiary information
    private String agencebeneficiaire;
    private String numcomptebeneficiaire;
    private String clebeneficiaire;
    private String nomComm;
    private String adrComm;

    // TPE payment date and amount
    private String date_paiement;
    private String heurePaiement;
    private String montantACompenser;
}