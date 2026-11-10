package com.Atos.Webaccess.Domain.Autorisation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "AUTORISATIONS", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Autorisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDAUTORISATION")
    private Integer idAutorisation;

    @Column(name = "NOMBREPRELEVEMENT")
    private Integer nombrePrelevement;

    @Column(name = "RIBBENEFICIAIRE", length = 20)
    private String ribBeneficiaire;

    @Column(name = "DATEPREMIERPRELEVEMENT")
    private LocalDate datePremierPrelevement;

    @Column(name = "DATEEXECUTION")
    private LocalDate dateExecution;

    @Column(name = "DATEFACTURE")
    private LocalDate dateFacture;

    @Column(name = "DATESAISIEAUTO")
    private LocalDate dateSaisie;
    
    @Column(name = "RIBTIRE", length = 20)
    private String ribTire;

    @Column(name = "MONTANTPRELEVEMENT", length = 20)
    private String montantPrelevement;

    @Column(name = "NOMTIRE", length = 50)
    private String nomTire;

    @Column(name = "ADRESSETIRE", length = 70)
    private String adresseTire;

    @Column(name = "ETAT")
    private Integer etat;

    @Column(name = "NOMBENEFICIAIRE", length = 50)
    private String nomBeneficiaire;

    @Column(name = "ADRESSEBENEFICIAIRE", length = 70)
    private String adresseBeneficiaire;

    @Column(name = "LIBELLE", length = 70)
    private String libelle;

    @Column(name = "DONNEESEMETTEUR", length = 70)
    private String donneesEmetteur;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "NUMEROEMETTEUR", length = 7)
    private String numeroEmetteur;

    @Column(name = "REFERENCE", length = 20)
    private String reference;

    @Column(name = "MOTIFANNULAUTO", length = 10)
    private String motifAnnulAuto;

    @Column(name = "NUMERODOSSIER", length = 50)
    private String numeroDossier;

    @Column(name = "RIBBENEFICIAIREINTERNE", length = 20)
    private String ribBeneficiaireInterne;
}