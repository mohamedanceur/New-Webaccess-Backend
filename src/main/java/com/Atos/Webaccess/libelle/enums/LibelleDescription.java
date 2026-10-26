package com.Atos.Webaccess.libelle.enums;

import java.util.ArrayList;
import java.util.List;

public enum LibelleDescription {
	
	OPENED_NO_REMISE("ETATLOT","OPENED_NO_REMISE","0"),
	OPENED_HAS_REMISE("ETATLOT","OPENED_HAS_REMISE","1"),
	CLOSED("ETATLOT","CLOSED","2"), // Lot Closed in applet etat Start
    LOI_IMI_CREATEED("ETATLOT","LOI_IMI_CREATEED","3"),/// etat SUCCES 
    LOI_IMI_ERREUR("ETATLOT","LOI_IMI_ERREUR","4"), // etat  ERROR  MAJOR 
    SEND_FTP("ETATLOT","SEND_FTP","5"), /// etat SUCCES 
    SEND_FTP_ERREUR("ETATLOT","SEND_FTP_ERREUR","6"), /// etat ERROR OF FTP  
    FTP_OK_NOT_RENAME("ETATLOT","FTP_OK_NOT_RENAME","7"),  // etat WARINING
    PARTIALY_GENERATED("ETATLOT","PARTIALY_GENERATED","8"),
	EFFET_SANS_IMAGE("ETATEFFET","EFFET_SANS_IMAGE","0"),
	EFFET_AVEC_IMAGE("ETATEFFET","EFFET_AVEC_IMAGE","1");
	
	
	public String key;
	public String value;
	public String code;
	
		
	private LibelleDescription(String key, String value, String code) {
		this.key = key;
		this.value = value;
		this.code = code;
	}
	
	public static List<LibelleDescription> valueOfKey(String key) {
		List<LibelleDescription> list = new ArrayList();
	    for (LibelleDescription e : values()) {
	        if (e.key.equals(key)) {
	        	list.add(e);
	        }
	    }
	    return list;
	}

}
