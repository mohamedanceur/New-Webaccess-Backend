package com.Atos.Webaccess.sib.bna.effet;


import jakarta.persistence.Column;


public class EffetBNA {

	@Column(name = "NEFF")
	private String numeff;

	@Column(name = "AGE")
	private String agence;

	@Column(name = "DPRE")
	private String datepre;

	@Column(name = "DECH")
	private String dateechance;
	
	@Column(name = "SOR")
	private String sor;

	public String getNumeff() {
		return numeff;
	}

	public void setNumeff(String numeff) {
		this.numeff = numeff;
	}

	public String getAgence() {
		return agence;
	}

	public void setAgence(String agence) {
		this.agence = agence;
	}

	public String getDatepre() {
		return datepre;
	}

	public void setDatepre(String datepre) {
		this.datepre = datepre;
	}

	public String getDateechance() {
		return dateechance;
	}

	public void setDateechance(String dateechance) {
		this.dateechance = dateechance;
	}

	public String getSor() {
		return sor;
	}

	public void setSor(String sor) {
		this.sor = sor;
	}
	
}
