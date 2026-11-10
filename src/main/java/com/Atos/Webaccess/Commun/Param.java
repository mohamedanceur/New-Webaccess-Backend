package com.Atos.Webaccess.Commun;

public class Param extends Object {


    public static String virement = "";
    public static String prelevement = "";
    public static String cheque = "";
    public static String carteretrai = "";
    public static String cartepaiment = "";
    public static String chargeBack="";
    public static String effet = "";
    public static String pMobile = "";
    

	public static String reel_path = "";
    public static String leParamOpCarteAValider = "";
    public static String ParamOpCarteValide = "";
    public static String ParamEffetAValider = "";
    public static String ParamEffetValide = "";
    public static String paramPrelevementAValider = "";
    public static String paramPrelevementValide = "";
    private static String paramVirementAValider = "";
    private static String ParamVirementValide = "";


    public static String leEtablissement = "";
    public static String MonCodeAgence = "";
    public static String carte = "";
    public static String context_path = "";

    private static String ParamAnnulOpr = "";
    private static String ParamAnnulOprRej = "";
    private static String ParamRejetOpr = "";
    private static String ParamReprOpr = "";
    private static String host = "";

    private static String opCarteGAB = "";
    private static String opCarteTPE = "";
    private static String opCarteRemTPE = "";
    private static String opChargeBack="";

    private static String opVirementCli = "";
    private static String opVirementBaB = "";
    private static String opVirementEtr = "";

    private static String ParamPlafondVirement = "";


    private static String opEffetLettre = "";
    private static String opEffetBillet = "";

    private static String isbanquemere = ""; // 1 ou 0


    private static String devise = "";
    private static String fontPDF = "";
    //private static String logo = "";
    private static String nomBanque = "";


    public static String dialect = ""; // oracle / interbase

    public static String appliImage = "";
    public static String cheminImage = "";

    public static String protocol = "";
    public static String servername = "";
    public static String serverport = "";

    public static String opPrelevementAtt = "";

    public static String cheminLog = "";
    public static String nomLog = "";
    public static String driver = "";
    public static String logoBanque="";
    public static String cheminReports="";
    
    public static String genFichierSQL = "";
    public static String cheminFichierSQL = "";
    
    public static String enableHpassword = "";
    public static String validityHpassword = "";
    public static String nbLastHpassword = "";

    private static String accesapprove = "";
    private static String accesreport = "";
    private static String accesmailing = "";
    
    private static String linkNew = "";

    public static String enableConnexion = "";
    public static String nbLastConnexion = "";
    public static String timeout = "";
    
 
    private static String pathimageEffet = "";
    private static String fichierimageEffet = "";
    private static String reconciliationDatePresentationPosition = "";

	public static String getAccesmailing() {
		return accesmailing;
	}

	public static void setAccesmailing(String accesmailing) {
		Param.accesmailing = accesmailing;
	}

	/***************************************************************************/
    public static String getDialect() {
        return dialect;
    }

    public static void setDialect(String dialect) {

        //org.hibernate.dialect.OracleDialect
        //org.hibernate.dialect.InterbaseDialect

        String sd = dialect.substring(dialect.lastIndexOf(".") + 1,
                                      dialect.length());

        if (sd.toLowerCase().contains("OracleDialect".toLowerCase())) {
            Param.dialect = "oracle";
        } else if (sd.toLowerCase().contains("InterbaseDialect".toLowerCase())) {
            Param.dialect = "interbase";
        }else{

        Param.dialect = dialect;
        }
    }

    /***************************************************************************/

    public static String getVirement() {
        return virement;
    }

    public static void setVirement(String virement) {
        Param.virement = virement;
    }

    public static String getPrelevement() {
        return prelevement;
    }

    public static void setPrelevement(String prelevement) {
        Param.prelevement = prelevement;
    }

    public static String getCheque() {
        return cheque;
    }

    public static void setCheque(String cheque) {
        Param.cheque = cheque;
    }

    public static String getCarteretrai() {
        return carteretrai;
    }

    public static void setCarteretrai(String carteretrai) {
        Param.carteretrai = carteretrai;
    }

    public static void setCarte(String carte) {
        Param.carte = carte;
    }

    public static String getCarte() {
        return carte;
    }

    public static String getCartepaiment() {
        return cartepaiment;
    }

    public static void setCartepaiment(String cartepaiment) {
        Param.cartepaiment = cartepaiment;
    }

    public static String getEffet() {
        return effet;
    }

    public static void setEffet(String effet) {
        Param.effet = effet;
    }

    public static String getReel_path() {
        return reel_path;
    }

    public static void setReel_path(String reel_path) {
        Param.reel_path = reel_path;
    }

    public static String getLeParamOpCarteAValider() {
        return leParamOpCarteAValider;
    }

    public static void setLeParamOpCarteAValider(String leParamOpCarteAValider) {
        Param.leParamOpCarteAValider = leParamOpCarteAValider;
    }

    public static String getParamOpCarteValide() {
        return ParamOpCarteValide;
    }

    public static void setParamOpCarteValide(String ParamOpCarteValide) {
        Param.ParamOpCarteValide = ParamOpCarteValide;
    }


    public static String getParamEffetAValider() {
        return ParamEffetAValider;
    }

    public static void setParamEffetAValider(String ParamEffetAValider) {
        Param.ParamEffetAValider = ParamEffetAValider;
    }


    public static String getParamEffetValide() {
        return ParamEffetValide;
    }

    public static void setParamEffetValide(String ParamEffetValide) {
        Param.ParamEffetValide = ParamEffetValide;
    }

    public static String getLeEtablissement() {
        return leEtablissement;
    }

    public static void setLeEtablissement(String leEtablissement) {
        Param.leEtablissement = leEtablissement;
    }

    public String getMonCodeAgence() {
        return MonCodeAgence;
    }

    public void setMonCodeAgence(String monCodeAgence) {
        MonCodeAgence = monCodeAgence;
    }


    public String getContext_path() {
        return context_path;
    }

    public void setContext_path(String context_path) {
        this.context_path = context_path;
    }

    public String getParamAnnulOpr() {
        return ParamAnnulOpr;
    }

    public void setParamAnnulOpr(String paramAnnulOpr) {
        ParamAnnulOpr = paramAnnulOpr;
    }

    public String getParamAnnulOprRej() {
        return ParamAnnulOprRej;
    }

    public void setParamAnnulOprRej(String paramAnnulOprRej) {
        ParamAnnulOprRej = paramAnnulOprRej;
    }

    public String getParamRejetOpr() {
        return ParamRejetOpr;
    }

    public void setParamRejetOpr(String paramRejetOpr) {
        ParamRejetOpr = paramRejetOpr;
    }

    public String getParamReprOpr() {
        return ParamReprOpr;
    }

    public void setParamReprOpr(String paramReprOpr) {
        ParamReprOpr = paramReprOpr;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        Param.host = host;
    }

    /************************* Type Operation Carte ****************************/
    public String getOpCarteGAB() {
        return opCarteGAB;
    }

    public void setOpCarteGAB(String value) {
        this.opCarteGAB = value;
    }

    public String getOpCarteTPE() {
        return opCarteTPE;
    }

    public void setOpCarteTPE(String value) {
        this.opCarteTPE = value;
    }

    public String getOpCarteRemTPE() {
        return opCarteRemTPE;
    }

    public void setOpCarteRemTPE(String value) {
        this.opCarteRemTPE = value;
    }

    public String getOpVirementCli() {
        return opVirementCli;
    }

    public void setOpVirementCli(String value) {
        this.opVirementCli = value;
    }

    public String getOpVirementBaB() {
        return opVirementBaB;
    }

    public void setOpVirementBaB(String value) {
        this.opVirementBaB = value;
    }

    public String getOpVirementEtr() {
        return opVirementEtr;
    }

    public void setOpVirementEtr(String value) {
        this.opVirementEtr = value;
    }


    /***************************************************************************/

    /************************* Type Operation Effet ****************************/
    public String getOpEffetLettre() {
        return opEffetLettre;
    }

    public void setOpEffetLettre(String value) {
        this.opEffetLettre = value;
    }

    public String getOpEffetBillet() {
        return opEffetBillet;
    }

    public void setOpEffetBillet(String value) {
        this.opEffetBillet = value;
    }

    /***************************************************************************/

    public String getIsbanquemere() {
        return isbanquemere;
    }

    public void setIsbanquemere(String value) {
        this.isbanquemere = value;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String value) {
        this.devise = value;
    }

    /***************************************************************************/

    public static String getParamVirementAValider() {
        return paramVirementAValider;
    }

    public static void setParamVirementAValider(String paramVirementAValider) {
        Param.paramVirementAValider = paramVirementAValider;
    }

    public static String getParamVirementValide() {
        return ParamVirementValide;
    }

    public static void setParamVirementValide(String ParamVirementValide) {
        Param.ParamVirementValide = ParamVirementValide;
    }

    public static String getParamPlafondVirement() {
        return ParamPlafondVirement;
    }

    public static void setParamPlafondVirement(String ParamPlafondVirement) {
        Param.ParamPlafondVirement = ParamPlafondVirement;
    }

    public static String getFontPDF() {
        return fontPDF;
    }

    public static void setFontPDF(String fontPDF) {
        Param.fontPDF = fontPDF;
    }

    /*public static String getLogo() {
        return logo;
    }

    public static void setLogo(String logo) {
        Param.logo = logo;
    }*/

    public static String getNomBanque() {
        return nomBanque;
    }

    public static void setNomBanque(String nomBanque) {
        Param.nomBanque = nomBanque;
    }


    public static String getAppliImage() {
        return appliImage;
    }

    public static void setAppliImage(String appliImage) {
        Param.appliImage = appliImage;
    }

    public static String getCheminImage() {
        return cheminImage;
    }

    public static void setCheminImage(String cheminImage) {
        Param.cheminImage = cheminImage;
    }

    public static String getServername() {
        return servername;
    }

    public static void setServername(String servername) {
        Param.servername = servername;
    }


    public static String getServerport() {
        return serverport;
    }

    public static void setServerport(String serverport) {
        Param.serverport = serverport;
    }

    public static String getOpPrelevementAtt() {
        return opPrelevementAtt;
    }

    public static void setOpPrelevementAtt(String opPrelevementAtt) {
        Param.opPrelevementAtt = opPrelevementAtt;
    }


    public static String getParamPrelevementAValider() {
        return paramPrelevementAValider;
    }

    public static void setParamPrelevementAValider(String
            paramPrelevementAValider) {
        Param.paramPrelevementAValider = paramPrelevementAValider;
    }

    public static String getParamPrelevementValide() {
        return paramPrelevementValide;
    }

    public static void setParamPrelevementValide(String paramPrelevementValide) {
        Param.paramPrelevementValide = paramPrelevementValide;
    }

    public static String getCheminLog() {
        return cheminLog;
    }

    public static void setCheminLog(String cheminLog) {
        Param.cheminLog = cheminLog;
    }

    public static String getNomLog() {
        return nomLog;
    }

    public static void setNomLog(String nomLog) {
        Param.nomLog = nomLog;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        Param.driver = driver;
    }

    public static String getGenFichierSQL() {
        return genFichierSQL;
    }
    public static void setGenFichierSQL(String value) {
        Param.genFichierSQL = value;
    }

    public static String getCheminFichierSQL() {
        return cheminFichierSQL;
    }
    public static void setCheminFichierSQL(String value) {
        Param.cheminFichierSQL = value;
    }
    public static String getEnableHpassword() {
        return enableHpassword;
    }
    public static void setEnableHpassword(String value) {
        Param.enableHpassword = value;
    }
    
    public static String getValidityHpassword() {
        return validityHpassword;
    }
    public static void setValidityHpassword(String value) {
        Param.validityHpassword = value;
    }
    public static String getNbLastHpassword() {
        return nbLastHpassword;
    }
    public static void setNbLastHpassword(String value) {
        Param.nbLastHpassword = value;
    }

    public static String getAccesapprove() {
        return accesapprove;
    }
    public static void setAccesapprove(String value) {
        Param.accesapprove = value;
    }
    public static String getAccesreport() {
        return accesreport;
    }
    public static void setAccesreport(String value) {
        Param.accesreport = value;
    }
	public static String getLogoBanque() {
		return logoBanque;
	}

	public static void setLogoBanque(String logoBanque) {
		Param.logoBanque = logoBanque;
	}

	/**
	 * @return the cheminReports
	 */
	public static String getCheminReports() {
		return cheminReports;
	}

	/**
	 * @param cheminReports the cheminReports to set
	 */
	public static void setCheminReports(String cheminReports) {
		Param.cheminReports = cheminReports;
	}

	public static String getLinkNew() {
		return linkNew;
	}

	public static void setLinkNew(String linkNew) {
		Param.linkNew = linkNew;
	}

	public static String getEnableConnexion() {
		return enableConnexion;
	}

	public static void setEnableConnexion(String enableConnexion) {
		Param.enableConnexion = enableConnexion;
	}

	public static String getNbLastConnexion() {
		return nbLastConnexion;
	}

	public static void setNbLastConnexion(String nbLastConnexion) {
		Param.nbLastConnexion = nbLastConnexion;
	}
	
	// reconciliation bna
	public static String getReconciliationDatePresentationPosition() {
		return reconciliationDatePresentationPosition;
	}

	public static void setReconciliationDatePresentationPosition(String reconciliationDatePresentationPosition) {
		Param.reconciliationDatePresentationPosition = reconciliationDatePresentationPosition;
	}
	
	public static String getPMobile() {
		return pMobile;
	}

	public static void setPMobile(String pMobile) {
		Param.pMobile = pMobile;
	}

	public static String getChargeBack() {
		return chargeBack;
	}

	public static void setChargeBack(String chargeBack) {
		Param.chargeBack = chargeBack;
	}

	public static String getOpChargeBack() {
		return opChargeBack;
	}

	public static void setOpChargeBack(String opChargeBack) {
		Param.opChargeBack = opChargeBack;
	}

	public static String getTimeout() {
		return timeout;
	}

	public static void setTimeout(String timeout) {
		Param.timeout = timeout;
	}

	public static String getPathimageEffet() {
		return pathimageEffet;
	}

	public static void setPathimageEffet(String pathimageEffet) {
		Param.pathimageEffet = pathimageEffet;
	}

	public static String getFichierimageEffet() {
		return fichierimageEffet;
	}

	public static void setFichierimageEffet(String fichierimageEffet) {
		Param.fichierimageEffet = fichierimageEffet;
	}

	public static String getProtocol() {
		return protocol;
	}

	public static void setProtocol(String protocol) {
		Param.protocol = protocol;
	}


	
}
