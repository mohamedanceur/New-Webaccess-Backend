package com.Atos.Webaccess.User.Entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "HPASSWORD", schema = "dbo")
public class Hpassword implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2586120277445122076L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDPWD", nullable = false, unique = true, updatable = false)
	private Integer id;
	
	@Column(name = "idutilisateur", nullable = false)
	private Integer idutilisateur;
	
	
	@Column(name = "motdepasse" , nullable = true , unique = false )
	private String motdepasse;
	
	@Column(name = "statut" , nullable = true , unique = false )
	private Integer statut;
	
	@Column(name = "datesaisie" , nullable = true , unique = false )
	private Date datesaisie;

	
	
}
