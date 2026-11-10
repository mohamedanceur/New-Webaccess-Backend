package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COMPTES", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Comptes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCOMPTE")
    private Integer idcompte;

    @Column(name = "TELEFIXE", length = 12)
    private String telefixe;

    @Column(name = "ETATCOMPTE", length = 40)
    private String etatcompte;

    @Column(name = "TYPEIDCOMPTE", length = 1)
    private String typeidcompte;

    @Column(name = "NOMENTREPRISE", length = 50)
    private String nomentreprise;

    @Column(name = "TYPECOMPTE", length = 1)
    private String typecompte;

    @Column(name = "PRENOM", length = 50)
    private String prenom;

    @Column(name = "NUMCOMPTE", length = 20)
    private String numcompte;

    @Column(name = "TELETRAVAIL", length = 12)
    private String teletravail;

    @Column(name = "ADRESSESIEGESOCIALE", length = 70)
    private String adressesiegesociale;

    @Column(name = "TYPEPIECEIDENTITE", length = 70)
    private String typepieceidentite;

    @Column(name = "DATENAISSANCE", length = 10)
    private String datenaissance;

    @Column(name = "NUMTELECOPIEUR", length = 12)
    private String numtelecopieur;

    @Column(name = "NOM", length = 50)
    private String nom;

    @Column(name = "TYPEIDENTIFICATION", length = 1)
    private String typeidentification;

    @Column(name = "NATUREJURIDIQUE", length = 50)
    private String naturejuridique;

    @Column(name = "NUMTELEX", length = 12)
    private String numtelex;

    @Column(name = "DATEINSRC", length = 10)
    private String dateinsrc;

    @Column(name = "NUMTELEPHONEP", length = 12)
    private String numtelephonep;

    @Column(name = "NUMTELEPHONE", length = 12)
    private String numtelephone;

    @Column(name = "DATELIMITEPI", length = 10)
    private String datelimitepi;

    @Column(name = "CODEAGENCE", length = 20)
    private String codeagence;

    @Column(name = "CLERIB", length = 2)
    private String clerib;

    @Column(name = "DATEDELIVRERPI", length = 10)
    private String datedelivrerpi;

    @Column(name = "NUMPIECEIDENTITE", length = 12)
    private String numpieceidentite;

    @Column(name = "ADRESSE", length = 70)
    private String adresse;

    @Column(name = "TELECELLULAIRE", length = 12)
    private String telecellulaire;

    @Column(name = "RAISONSOCIALE", length = 50)
    private String raisonsociale;

    @Column(name = "NUMIDFISCALE", length = 12)
    private String numidfiscale;

    @Column(name = "CODEBANQUE", length = 10)
    private String codebanque;
}