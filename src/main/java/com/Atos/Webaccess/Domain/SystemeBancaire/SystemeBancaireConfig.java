
package com.Atos.Webaccess.Domain.SystemeBancaire;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemeBancaireConfig {

    private Integer taillecodebanque;
    private Integer taillecodeagence;
    private Integer tailleFinaleCodeAgence;
    private Integer taillenumerocompte;
    private Integer tailleclerib;
    private String codedevise;

    private Integer taillemontant;
    private Integer taillenumerocheque;
    private Integer delaireglement;

    private Integer poscodebanquerib;
    private Integer poscodeagencerib;
    private Integer posnumerocompterib;
    private Integer poscleribrib;

    private Integer taillerib;
    private Integer taillerio;
    private Integer tailleiio;
    private String typenumerocompte;

    // Legacy fixed values
    private Integer taillenumerocarte = 16;
    private Integer taillenumeroeffet = 7;
    private Integer taillenumerointerne = 10;
    private Integer taillerefinit = 16;
    private Integer tailletelephone = 10;
    private String motif_rejet_default = "MC000";
    private Integer taillenumeroprelevement = 7;
    private Integer tailledonneesemetteur = 17;
}
