package com.Atos.Webaccess.Domain.parametre;

import java.io.Serializable;

import jakarta.persistence.*;



@Entity
@Table(name = "parametre", schema = "dbo")
public class Parametre implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1603793931610173214L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDENTIFIANTPARAMETRE")
	private Long identifiantParametre;
	
	
	@Column(name = "LOTSDIRECTORY")
	private String lotsDirectory="";
	
	@Column(name = "IMISDIRECTORY")
	private String iMIsDirectory="";
	
	@Column(name = "IMAGESDIRECTORY")
	private String imagesDirectory="";
	
	@Column(name = "WORKDIRECTORY")
	private String workDirectory="";
	
	@Column(name = "CODEBANQUE")
	private String codeBanque="";
	
	@Column(name = "POSTEUTILISATEUR", columnDefinition = "varchar(3) default '001'")
	private String posteUtilisateur="";  // Poste d'utilisateur par default un entier de taille de trois example : '001'

	@Column(name = "TEMPLATEDIRECTORY")
	private String templateDirectory="";
	
	@Column(name = "BORDEREAUDIRECTORY")
	private String bordereauDirectory="";
	
	
	@Column(name = "HPASSWORD")
	private int hpassword;
	
	@Column(name = "PW_VALIDITY")
	private int pWvalidity;
	
	
	@Column(name = "TEXTTOBEPRINTED")
	private String textToBePrinted="";
	
	
	public Long getIdentifiantParametre() {
		return identifiantParametre;
	}

	public void setIdentifiantParametre(Long identifiantParametre) {
		this.identifiantParametre = identifiantParametre;
	}

	public String getLotsDirectory() {
		return lotsDirectory;
	}

	public void setLotsDirectory(String lotsDirectory) {
		this.lotsDirectory = lotsDirectory;
	}

	public String getiMIsDirectory() {
		return iMIsDirectory;
	}

	public void setiMIsDirectory(String iMIsDirectory) {
		this.iMIsDirectory = iMIsDirectory;
	}

	public String getImagesDirectory() {
		return imagesDirectory;
	}

	public void setImagesDirectory(String imagesDirectory) {
		this.imagesDirectory = imagesDirectory;
	}

	public String getWorkDirectory() {
		return workDirectory;
	}

	public void setWorkDirectory(String workDirectory) {
		this.workDirectory = workDirectory;
	}

	public String getCodeBanque() {
		return codeBanque;
	}

	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
	}

	public String getPosteUtilisateur() {
		return posteUtilisateur;
	}

	public void setPosteUtilisateur(String posteUtilisateur) {
		this.posteUtilisateur = posteUtilisateur;
	}

	public String getTemplateDirectory() {
		return templateDirectory;
	}

	public void setTemplateDirectory(String templateDirectory) {
		this.templateDirectory = templateDirectory;
	}

	public String getBordereauDirectory() {
		return bordereauDirectory;
	}

	public void setBordereauDirectory(String bordereauDirectory) {
		this.bordereauDirectory = bordereauDirectory;
	}

	public int getHpassword() {
		return hpassword;
	}

	public void setHpassword(int hpassword) {
		this.hpassword = hpassword;
	}

	public int getpWvalidity() {
		return pWvalidity;
	}

	public void setpWvalidity(int pWvalidity) {
		this.pWvalidity = pWvalidity;
	}

	public String getTextToBePrinted() {
		return textToBePrinted;
	}

	public void setTextToBePrinted(String textToBePrinted) {
		this.textToBePrinted = textToBePrinted;
	}
	
}
