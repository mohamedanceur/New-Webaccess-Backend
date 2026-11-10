package com.Atos.Webaccess.Commun;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Priv {

    // Operation permissions
    private int modif_op_carte = 0;
    private int represt_op_carte = 0;
    private int rejet_op_carte = 0;
    private int annul_op_carte = 0;

    private int modif_op_cheque = 0;
    private int represt_op_cheque = 0;
    private int rejet_op_cheque = 0;
    private int annul_op_cheque = 0;

    private int modif_op_effet = 0;
    private int represt_op_effet = 0;
    private int rejet_op_effet = 0;
    private int annul_op_effet = 0;

    private int modif_op_prelev = 0;
    private int represt_op_prelev = 0;
    private int rejet_op_prelev = 0;
    private int annul_op_prelev = 0;

    private int modif_op_virm = 0;
    private int represt_op_virm = 0;
    private int regul_op_virm = 0;
    private int annul_op_virm = 0;
    private int rejet_op_virm = 0;

    // Consultation permissions
    private int rech_carte = 0;
    private int rech_prelev = 0;
    private int rech_virm = 0;
    private int rech_cheq = 0;
    private int rech_effet = 0;
    private int rech_pMobile = 0;

    // Pending direct debit permissions
    private int prelev_att_modif = 0;
    private int prelev_att_supprim = 0;
    private int prelev_att_valid = 0;
    private int prelev_att_ajout = 0;

    // Pending card permissions
    private int carte_att_modif = 0;
    private int carte_att_supprim = 0;
    private int carte_att_valid = 0;
    private int carte_att_ajout = 0;

    // Pending effect permissions
    private int effet_att_modif = 0;
    private int effet_att_supprim = 0;
    private int effet_att_valid = 0;
    private int effet_att_ajout = 0;

    // Pending transfer permissions
    private int virement_att_modif = 0;
    private int virement_att_supprim = 0;
    private int virement_att_valid = 0;
    private int virement_att_ajout = 0;

    // P-Mobiles operation permissions
    private int modif_op_pMobile = 0;
    private int represt_op_pMobile = 0;
    private int rejet_op_pMobile = 0;
    private int annul_op_pMobile = 0;

    // Automatic direct debit permissions
    private int autoPrelev_att_modif = 0;
    private int autoPrelev_att_supprim = 0;
    private int autoPrelev_att_valid = 0;
    private int autoPrelev_att_ajout = 0;

    private int autoPrelev_Valid_annulation = 0;
    private int autoPrelev_Decaler_prelev = 0;
}