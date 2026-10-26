package com.Atos.Webaccess.sib.agb.comptes;



public class CompteAGB {

	/**
	 * 
	 */

	private String numeroCompte;
	
	private String nom;	
	
	private String prenom;	
	
	private String adresse;	
	
	private String cloture;
	
	private String enOpposition;
	
	private String solde;
	
	//private String taux;

	public String getNumeroCompte() {
		return numeroCompte;
	}

	public void setNumeroCompte(String retail) {
		this.numeroCompte = retail;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/*public String getTaux() {
		return taux;
	}

	public void setTaux(String taux) {
		this.taux = taux;
	}*/

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adr) {
		this.adresse = adr;
	}

	public String getCloture() {
		return cloture;
	}

	public void setCloture(String cloture) {
		this.cloture = cloture;
	}

	public String getEnOpposition() {
		return enOpposition;
	}

	public void setEnOpposition(String oppos) {
		this.enOpposition = oppos;
	}

	public String getSolde() {
		return solde;
	}

	public void setSolde(String solde) {
		this.solde = solde;
	}
		
	
}
