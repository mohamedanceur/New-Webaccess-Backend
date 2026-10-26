package com.Atos.Webaccess.Constants;

public class AppConstants {

	public static final  int MAX_ALLOWED_INACTIVE_SESSION_MIN = 5;
	public static final String RESPONSE_MESSAGE_KEY = "message";
	public static final String AUTH_TOKEN_NAME = "authToken";
	public static final String FIRST_LOGIN = "firstLogin";
	public static final String USER_KEY = "authUser";
	public static final String CLIENT_IP_ADRESS =  "clientIp";
	public static final String USER_ID =  "userId"; 
	public static final String FTP = "FTP";
	public static final String SFTP = "SFTP";
	public static final String AUTHORIZATION = "Authorization";
	public static final String WRONG_STATUS = "wrong_status";
	public static final String PW_EXPIRED = "pw_expired";
	public static final  int STATUS_ACTIF_TRUE = 1 ;
	public static final  int STATUS_ACTIF_FALSE = 0 ;
	public static final String DOMAIN_CODE_BANQUE = "domain.codeBanque";
	public static final String DOMAIN_CODE_AGENCE_PILOTE= "domain.codeAgencePilote";
	public static final String DOMAIN_LIBELLE_BANQUE = "domain.libelleBanque";
	public static final String DOMAIN_LIBELLE_AGENCE_PILOTE = "domain.libelleAgencePilote";
	public static final  int RETURN_SUCCES = 1 ;
	public static final  int RETURN_ERROR_CONSTRAINT = 2 ;
	public static final  int RETURN_ERROR_DATABASE = 3 ;
	public static final  int OLD_PASSWORD_NOT_MATCHED = 4;
	/***
	 * 
	 * 
	 * Etat Lot status
	 */

	public static final  int LOT_OPENED_NO_REMISE = 0 ;
	public static final  int LOT_HAS_REMISE = 1 ;
	public static final  int LOT_CLOSED = 2 ;  // Lot Closed in applet etat Start
	public static final  int LOT_FILE_LOI_IMI_CREATEED = 3 ; /// etat SUCCES 
	public static final  int LOT_FILE_LOI_IMI_ERREUR = 4 ;  // etat  ERROR  MAJOR 
	public static final int LOT_EFFET_DELTA_OUVERT = 12;// Lot d'effets delta ouvert et contient des effets en attente d'image
	/***
	 * 
	 *  FTP STATUS ARE CONSIDERED ONLY IF LOI AND IMI ARE PROPERLY CREATED 
	 * **/
	public static final  int LOT_FILE_LOI_IMI_SEND_FTP = 5 ;  /// etat SUCCES  
	public static final  int LOT_FILE_LOI_IMI_SEND_FTP_ERREUR = 6 ; /// etat ERROR OF FTP  
	public static final  int LOT_FILE_LOI_IMI_SEND_FTP_OK_NOT_RENAMED = 7; /// etat SUCCES
	public static final  int LOT_FILE_LOI_IMI_PARTIALY_GENERATED = 8; /// etat SUCCES

	/***
	 * 
	 *  EFFET STATUS
	 * **/
	
	public static final int ETAT_EFFET_EN_ATTENTE_IMAGE = 2;
	public static final int ETAT_EFFET_ATTACHE = 10;
	
	/***
	 * 
	 *  CHEQUE STATUS
	 * **/
	
	public static final int BAD_FORMATED_CHEQUE = 999;

	/***
	 * 
	 *  REMISE STATUS
	 * **/
	
	public static final int BAD_FORMATED_REMISE = 999;




	
	

}
