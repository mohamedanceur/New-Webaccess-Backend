package com.Atos.Webaccess.Approve.common;



/**
 * Stores configuration parameters for the Approve module.
 *
 * Static fields are intentionally retained because the legacy application
 * accesses these parameters globally.
 */
public class ParamApprove {

    // Bank and agency
    public static String CodeBanque = "";
    public static String CodeAgence = "";

    // Image paths
    public static String PathImagesOperationsBD = "";
    public static String PathImagesOperations = "";

    // Server configuration
    public static String reel_path = "";
    public static String context_path = "";
    private static String host = "";
    public static String servername = "";
    public static String serverport = "";

    // Cheque origins
    public static int OrigineAllerSans = 1;
    public static int OrigineAllerAvec = 2;
    public static int OrigineAllerAvecSans = 3;
    public static int OrigineRetourSans = 4;
    public static int OrigineRetourAvec = 5;
    public static int OrigineRetourAvecSans = 6;
    public static int OrigineRetourPapier = 7;
    public static int OrigineAllerPapier = 8;

    // SIB configuration
    private static int ExistConnexSIB;
    private static String NomSIB = "";
    private static String TypeSIB = "";
    private static int TypeBddSIB;



    // Database types
    public static int TypeSGBD_Interbase = 1;
    public static int TypeSGBD_PostGreSQL = 2;
    public static int TypeSGBD_Oracle = 3;
    public static int TypeSGBD_SQLServeur = 4;
    public static int TypeSGBD_Informix = 5;

    public static String PubKey =
            "{6AF0EFC6-B937-4704-A430-319EB93F4C12}";

    // SIB database credentials
    private static String urlConnectionSIB;
    private static String usernameSIB;
    private static String passwordSIB;

    // Signature and image configuration
    private static String pathsignature;
    private static String pathphoto;
    private static String typeImage = "";

    // Cheque verification
    public static int deepCheck;
    private static String typechequeAD = "";
    private static String typechequeCB = "";

    // Signature database credentials
    private static String urlConnectionSIBSignature;
    private static String usernameSIBSignature;
    private static String passwordSIBSignature;

    // ---------------------------------------------------------------------
    // Bank and agency
    // ---------------------------------------------------------------------

    public static String getCodeBanque() {
        return CodeBanque;
    }

    public static void setCodeBanque(String value) {
        CodeBanque = value;
    }

    public static String getCodeAgence() {
        return CodeAgence;
    }

    public static void setCodeAgence(String value) {
        CodeAgence = value;
    }

    public static String getPathImagesOperationsBD() {
        return PathImagesOperationsBD;
    }

    public static void setPathImagesOperationsBD(String value) {
        PathImagesOperationsBD = value;
    }

    public static String getPathImagesOperations() {
        return PathImagesOperations;
    }

    public static void setPathImagesOperations(String value) {
        PathImagesOperations = value;
    }

    // ---------------------------------------------------------------------
    // Server configuration
    // ---------------------------------------------------------------------

    public static String getReel_path() {
        return reel_path;
    }

    public static void setReel_path(String value) {
        reel_path = value;
    }

    public String getContext_path() {
        return context_path;
    }

    public void setContext_path(String value) {
        context_path = value;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String value) {
        host = value;
    }

    public static String getServername() {
        return servername;
    }

    public static void setServername(String value) {
        servername = value;
    }

    public static String getServerport() {
        return serverport;
    }

    public static void setServerport(String value) {
        serverport = value;
    }

    // ---------------------------------------------------------------------
    // SIB configuration
    // ---------------------------------------------------------------------

    public int getExistConnexSIB() {
        return ExistConnexSIB;
    }

    public void setExistConnexSIB(int value) {
        ExistConnexSIB = value;
    }

    public String getNomSIB() {
        return NomSIB;
    }

    public void setNomSIB(String value) {
        NomSIB = value;
        setTypeSIB();
        setTypeBddSIB();
    }

    public String getTypeSIB() {
        return TypeSIB;
    }

    public void setTypeSIB() {
        String type = "";

        if (NomSIB != null && !NomSIB.isEmpty()) {
            if (NomSIB.equalsIgnoreCase("SIB")) {
                type = "MoneCheck";
            } else if (NomSIB.equalsIgnoreCase("Delta")) {
                type = "Delta";
            } else if (NomSIB.equalsIgnoreCase("CPA")) {
                type = "CPA";
            } else if (NomSIB.equalsIgnoreCase("BEA")) {
                type = "BEA";
            } else if (NomSIB.equalsIgnoreCase("AGB")) {
                type = "AGB";
            } else if (NomSIB.equalsIgnoreCase("BNP")) {
                type = "BNP";
            } else if (NomSIB.equalsIgnoreCase("Calyon")) {
                type = "Calyon";
            } else if (NomSIB.equalsIgnoreCase("HSBC")) {
                type = "HSBC";
            } else if (NomSIB.equalsIgnoreCase("Socket")) {
                type = "";
            }
        }

        TypeSIB = type;
    }

    public int getTypeBddSIB() {
        return TypeBddSIB;
    }

    public void setTypeBddSIB() {
        int typeBdd = 0;

        if (TypeSIB != null && !TypeSIB.isEmpty()) {
            if (TypeSIB.equalsIgnoreCase("MoneCheck")) {
                typeBdd = TypeSGBD_Interbase;
            } else if (TypeSIB.equalsIgnoreCase("Delta")) {
                typeBdd = TypeSGBD_Informix;
            } else if (TypeSIB.equalsIgnoreCase("CPA")) {
                typeBdd = TypeSGBD_Informix;
            } else if (TypeSIB.equalsIgnoreCase("BEA")) {
                typeBdd = TypeSGBD_Informix;
            } else if (TypeSIB.equalsIgnoreCase("AGB")) {
                typeBdd = TypeSGBD_SQLServeur;
            } else if (TypeSIB.equalsIgnoreCase("BNP")) {
                typeBdd = TypeSGBD_Interbase;
            } else if (TypeSIB.equalsIgnoreCase("Calyon")) {
                typeBdd = TypeSGBD_Interbase;
            } else if (TypeSIB.equalsIgnoreCase("HSBC")) {
                typeBdd = TypeSGBD_Oracle;
            }
        }

        TypeBddSIB = typeBdd;
    }

    // ---------------------------------------------------------------------
    // SIB database credentials
    // ---------------------------------------------------------------------

    public String geturlConnectionSIB() {
        return urlConnectionSIB;
    }

    public void seturlConnectionSIB(String value) {
        urlConnectionSIB = value;
    }

    public String getusernameSIB() {
        return usernameSIB;
    }

    public void setusernameSIB(String value) {
        usernameSIB = value;
    }

    public String getpasswordSIB() {
        return passwordSIB;
    }

    public void setpasswordSIB(String value) {
        passwordSIB = value;
    }

    // ---------------------------------------------------------------------
    // Signature and image configuration
    // ---------------------------------------------------------------------

    public String getpathsignature() {
        return pathsignature;
    }

    public void setpathsignature(String value) {
        pathsignature = value;
    }

    public String getpathphoto() {
        return pathphoto;
    }

    public void setpathphoto(String value) {
        pathphoto = value;
    }

    public static int getDeepCheck() {
        return deepCheck;
    }

    public static void setDeepCheck(int value) {
        deepCheck = value;
    }

    public static String getTypechequeAD() {
        return typechequeAD;
    }

    public static void setTypechequeAD(String value) {
        typechequeAD = value;
    }

    public static String getTypechequeCB() {
        return typechequeCB;
    }

    public static void setTypechequeCB(String value) {
        typechequeCB = value;
    }

    public String getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(String value) {
        if (value != null && !value.isEmpty()) {
            value = value.equalsIgnoreCase("BLOB")
                    ? "BLOB"
                    : "FICHIER";
        }

        typeImage = value;
    }

    // ---------------------------------------------------------------------
    // Signature database credentials
    // ---------------------------------------------------------------------

    public String getUrlConnectionSIBSignature() {
        return urlConnectionSIBSignature;
    }

    public void setUrlConnectionSIBSignature(String value) {
        urlConnectionSIBSignature = value;
    }

    public String getUsernameSIBSignature() {
        return usernameSIBSignature;
    }

    public void setUsernameSIBSignature(String value) {
        usernameSIBSignature = value;
    }

    public String getPasswordSIBSignature() {
        return passwordSIBSignature;
    }

    public void setPasswordSIBSignature(String value) {
        passwordSIBSignature = value;
    }
}

