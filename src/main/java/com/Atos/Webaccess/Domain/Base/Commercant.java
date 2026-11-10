package com.Atos.Webaccess.Domain.Base;

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

@Entity
@Table(name = "COMMERCANTS", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Commercant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCOMMERCANT")
    private Integer idcommercant;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "TYPECONTACT", length = 3)
    private String typecontact;

    @Column(name = "FAX", length = 15)
    private String fax;

    @Column(name = "NUMEROREGISTRECOMMERCE", length = 30)
    private String numeroregistrecommerce;

    @Column(name = "ADRESSECOMMERCE2", length = 37)
    private String adressecommerce2;

    @Column(name = "ADRESSECOMMERCE4", length = 20)
    private String adressecommerce4;

    @Column(name = "NOM", length = 30)
    private String nom;

    @Column(name = "ADRESSECOMMERCE3", length = 2)
    private String adressecommerce3;

    @Column(name = "NOMCONTACT2", length = 25)
    private String nomcontact2;

    @Column(name = "TITRECONTACTPRINCIPAL", length = 15)
    private String titrecontactprincipal;

    @Column(name = "NUMEROTELEPHONEMOBIL", length = 15)
    private String numerotelephonemobil;

    @Column(name = "LIBELLECOMMERCE", length = 40)
    private String libellecommerce;

    @Column(name = "ADRESSECOMMERCE7", length = 15)
    private String adressecommerce7;

    @Column(name = "ADRESSECOMMERCE1", length = 3)
    private String adressecommerce1;

    @Column(name = "CATEGORIECOMMERCE", length = 8)
    private String categoriecommerce;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "ADRESSECOMMERCE6", length = 3)
    private String adressecommerce6;

    @Column(name = "PLAFOND", length = 12)
    private String plafond;

    @Column(name = "RIB", length = 20)
    private String rib;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "TYPEDOCUMENT", length = 3)
    private String typedocument;

    @Column(name = "NUMEROCONTRAT", length = 15)
    private String numerocontrat;

    @Column(name = "ADRESSECOMMERCE5", length = 20)
    private String adressecommerce5;

    @Column(name = "NBRANNEECOMMERCE", length = 3)
    private String nbranneecommerce;

    @Column(name = "IDFISCALE", length = 15)
    private String idfiscale;

    @Column(name = "ADRESSECOMMERCE8", length = 5)
    private String adressecommerce8;

    @Column(name = "NUMEROTELEPHONE", length = 15)
    private String numerotelephone;

    @Column(name = "NOMCONTACTPRINCIPAL", length = 25)
    private String nomcontactprincipal;

    @Column(name = "RAISONSOCIALE", length = 30)
    private String raisonsociale;

    @Column(name = "ADRESSEEMAIL", length = 50)
    private String adresseemail;

    @Column(name = "STATUT")
    private Integer statut;
}