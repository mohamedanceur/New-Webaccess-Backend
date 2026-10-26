package com.Atos.Webaccess.User.Entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name="Hpassword")
public class Hpassword  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2586120277445122076L;


	@Id
	/*@GeneratedValue(strategy=GenerationType.SEQUENCE,  generator ="hpassword_generator")
	@SequenceGenerator(name="hpassword_generator", sequenceName="hpassword_seq",allocationSize=1)*/
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "IDPWD" , nullable = false , unique = true , updatable = false)
	private long id;
	
	@Column(name = "idutilisateur" , nullable = false , unique = false )
	private long idutilisateur;
	
	
	@Column(name = "motdepasse" , nullable = true , unique = false )
	private String motdepasse;
	
	@Column(name = "statut" , nullable = true , unique = false )
	private Integer statut;
	
	@Column(name = "datesaisie" , nullable = true , unique = false )
	private Date datesaisie;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public Integer getStatut() {
		return statut;
	}

	public void setStatut(Integer statut) {
		this.statut = statut;
	}

	public Date getDatesaisie() {
		return datesaisie;
	}

	public void setDatesaisie(Date datesaisie) {
		this.datesaisie = datesaisie;
	}

	public long getIdutilisateur() {
		return idutilisateur;
	}

	public void setIdutilisateur(long idutilisateur) {
		this.idutilisateur = idutilisateur;
	}
	
	
}
