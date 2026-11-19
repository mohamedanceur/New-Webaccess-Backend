package com.Atos.Webaccess.Approve;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
public class ApproveOperationDto {
    private Integer idoperation;
    private String ribtire;
    private String ribbeneficiaire;
    private BigDecimal montantoperation;
    private String devise;
    private String motifrejet;
    private String rio;
    private String referenceinterne;
    private Integer etat;
    private Integer origine;
    private String indicateurrejet;
    private String indicateurannulation;
    private String indicateurrepresentation;
    private Date datereglement;

    // Chèque / Effet details
    private String numeroInstrument;
    private String fichierimage;
    private String pathimage;
    private String libBanque;
    private String codeAval;

    private String statComp;
    private String ribAction;
    private Integer instrument;
}
