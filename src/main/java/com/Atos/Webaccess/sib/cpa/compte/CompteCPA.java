package com.Atos.Webaccess.sib.cpa.compte;

public class CompteCPA {

	private String agence;
	private String nom;
	private String adresse;
	private String numerodecompte;
	private String cle;
	private String cloture;
	private String interditChequier;
	private String libReduitOppCompte;
	private String libDetailOppCompte;
	public String getAgence() {
		return agence;
	}
	public void setAgence(String agence) {
		this.agence = agence;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getNumerodecompte() {
		return numerodecompte;
	}
	public void setNumerodecompte(String numerodecompte) {
		this.numerodecompte = numerodecompte;
	}
	public String getCle() {
		return cle;
	}
	public void setCle(String cle) {
		this.cle = cle;
	}

	public String getCloture() {
		return cloture;
	}
	public void setCloture(String cloture) {
		this.cloture = cloture;
	}
	public String getInterditChequier() {
		return interditChequier;
	}
	public void setInterditChequier(String interditChequier) {
		this.interditChequier = interditChequier;
	}
	public String getLibReduitOppCompte() {
		return libReduitOppCompte;
	}
	public void setLibReduitOppCompte(String libReduitOppCompte) {
		this.libReduitOppCompte = libReduitOppCompte;
	}
	public String getLibDetailOppCompte() {
		return libDetailOppCompte;
	}
	public void setLibDetailOppCompte(String libDetailOppCompte) {
		this.libDetailOppCompte = libDetailOppCompte;
	}
	@Override
	public String toString() {
		return "CompteCPA [agence=" + agence + ", nom=" + nom + ", adresse=" + adresse + ", numerodecompte="
				+ numerodecompte + ", cle=" + cle + ", cloture=" + cloture + ", interditChequier=" + interditChequier
				+ ", libReduitOppCompte=" + libReduitOppCompte + ", libDetailOppCompte=" + libDetailOppCompte + "]";
	}
	
	
	

}
