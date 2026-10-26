package com.Atos.Webaccess.User.Enums;

public enum RightType {
    
    
    /** this part if for a globale configuration of legacy application numerise.ini */
    
    //TRAITEMENT_ALLER,
    //TRAITEMET_RETOUR,
    //SAISIE_CODE_AGENCE_REMETENTTE,
    CONTROLE_CLE_RIB,
    //CREATION_MANUELLE_DES_CHEQUES,
    CALCULE_AUTOMATIC_CLE_RIB,
    //GESTION_DATE_ESCOMPTE,
    //GESTION_DATE_EMISSION,
    //ENDOS,
    //MESSAGE_ENTETE_ENDOS,
    //MESSAGE_FIN_ENDOS,
    CONTROLE_CLE_RIB_CHEQUE,
    //CONTROLE_NOMBRE_DE_CHEQUE_PAR_REMISE,
    //CONTROLE_NOMBRE_REMISE_PAR_LOT,
    IMPRESSION_BORDEREAU_DE_REMISE,
    //NOMBRE_EXAMPLAIRE_BORDEREAU_DE_REMISE,
    IMPRESSION_BORDEREAU_DE_LOT,
    NOMBRE_EXAMPLAIRE_BORDEREAU_DE_LOT,
    //CONNEXION_SIB,
    //CONNEXION_SIB_BLOQUANT,
    //CONTROLE_ETAT_CHEQUE,
    //CONTROLE_ETAT_COMPTE,
    //CONTROLE_AGENCE_REMETTANTE,
    
    
/**************************************************************************/
    
    /***
    * 
     * 
     * 
     * This part of Enumeration is for globale users right 
     */
    /***
     * 
      * 
      * 
      * This part of Enumeration is for globale users right 
      */
     MODULE_NUMERISE_CHEQUES,
     MODULE_NUMERISE_EFFETS,
     /***
      */
     MODULE_CONSULT,
     MODULE_CONSULT_LOT,
     MODULE_CONSULT_EFFET,
     CONSULT_LOT_EDIT,
     
     
     /** */
     MODULE_ADMINISTRATION,
     MODULE_NUMERISE,
     AFFICHE_NOTIFICATION,
     
     /***
      * 
       * 
       * 
       * This part of Enumeration is for HSBC right 
       */
     
     MODULE_BENEFICIAIRE,
     MODULE_ROLE,
     MODULE_GROUPE,
     MODULE_AGENCE,
     MODULE_USER,
     USER_CREATE,
     USER_UPDATE,
     USER_DELETE,
     USER_RESET_PASSWORD,
     USER_BLOCKED,
     MODULE_SETTINGS,
     BLOCK_SCAN_NUMERISE,
     MODULE_CORRESPONDANCES,
     MODULE_RCD
    
    

}
