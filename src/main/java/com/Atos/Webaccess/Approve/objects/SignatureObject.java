package com.Atos.Webaccess.Approve.objects;


import java.util.List;

public class SignatureObject {

    private String agence;
    private String numerocompte;
    private String etatcompte;
    private String nomprenomclient;
    private String adresseclient;
    private String villeclient;
    private String sold;
    private List listSignataire;
    private String url_docubase;
    private int err;
    private String type_mand;
    private String etat;

    // Standard getters and setters

    public String getAgence() {
        return agence;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getNumerocompte() {
        return numerocompte;
    }

    public void setNumerocompte(String numerocompte) {
        this.numerocompte = numerocompte;
    }

    public String getEtatcompte() {
        return etatcompte;
    }

    public void setEtatcompte(String etatcompte) {
        this.etatcompte = etatcompte;
    }

    public String getNomprenomclient() {
        return nomprenomclient;
    }

    public void setNomprenomclient(String nomprenomclient) {
        this.nomprenomclient = nomprenomclient;
    }

    public String getAdresseclient() {
        return adresseclient;
    }

    public void setAdresseclient(String adresseclient) {
        this.adresseclient = adresseclient;
    }

    public String getVilleclient() {
        return villeclient;
    }

    public void setVilleclient(String villeclient) {
        this.villeclient = villeclient;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public List getListSignataire() {
        return listSignataire;
    }

    public void setListSignataire(List listSignataire) {
        this.listSignataire = listSignataire;
    }

    public String getUrl_docubase() {
        return url_docubase;
    }

    public void setUrl_docubase(String url_docubase) {
        this.url_docubase = url_docubase;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getType_mand() {
        return type_mand;
    }

    public void setType_mand(String type_mand) {
        this.type_mand = type_mand;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    // Legacy methods retained for backward compatibility

    @Deprecated
    public int geterr() {
        return getErr();
    }

    @Deprecated
    public void seterr(int value) {
        setErr(value);
    }

    @Deprecated
    public String getagence() {
        return getAgence();
    }

    @Deprecated
    public void setagence(String value) {
        setAgence(value);
    }

    @Deprecated
    public String getnumerocompte() {
        return getNumerocompte();
    }

    @Deprecated
    public void setnumerocompte(String value) {
        setNumerocompte(value);
    }

    @Deprecated
    public String getetatcompte() {
        return getEtatcompte();
    }

    @Deprecated
    public void setetatcompte(String value) {
        setEtatcompte(value);
    }

    @Deprecated
    public String getnomprenomclient() {
        return getNomprenomclient();
    }

    @Deprecated
    public void setnomprenomclient(String value) {
        setNomprenomclient(value);
    }

    @Deprecated
    public String getadresseclient() {
        return getAdresseclient();
    }

    @Deprecated
    public void setadresseclient(String value) {
        setAdresseclient(value);
    }

    @Deprecated
    public String getvilleclient() {
        return getVilleclient();
    }

    @Deprecated
    public void setvilleclient(String value) {
        setVilleclient(value);
    }

    @Deprecated
    public String getsold() {
        return getSold();
    }

    @Deprecated
    public void setsold(String value) {
        setSold(value);
    }

    @Deprecated
    public List getlistSignataire() {
        return getListSignataire();
    }

    @Deprecated
    public void setlistSignataire(List value) {
        setListSignataire(value);
    }

    @Deprecated
    public String geturl_docubase() {
        return getUrl_docubase();
    }

    @Deprecated
    public void seturl_docubase(String value) {
        setUrl_docubase(value);
    }
}
