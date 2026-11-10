package com.Atos.Webaccess.Domain.Remises;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "remises", schema = "dbo")@Getter
@Setter
@NoArgsConstructor
public class Remises {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDREMISE")
    private Integer idremise;

    @Column(name = "NOM_ORDINATEUR", length = 50)
    private String nomOrdinateur;

    @Column(name = "ETATREMISE")
    private Integer etatremise;

    @Column(name = "FICHIERIMAGE", length = 64)
    private String fichierimage;

    @Column(name = "AGENCEREMETTANT", length = 20)
    private String agenceremettant;

    @Column(name = "NOMCLIENT", length = 50)
    private String nomclient;

    @Column(name = "RIBREMETTANT", length = 40)
    private String ribremettant;

    @Column(name = "DEVISE", length = 3)
    private String devise;

    @Column(name = "MONTANTREMISE", length = 20)
    private String montantremise;
    
    @Column(name = "NOM_UTILISATEUR", length = 50)
    private String nomUtilisateur;

    @Column(name = "TYPEREMISE", length = 3)
    private String typeremise;

    @Column(name = "AGENCEREMISE", length = 20)
    private String agenceremise;

    @Column(name = "IDLOT")
    private Integer idlot;

    @Column(name = "NOMBREOPERATION")
    private Integer nombreoperation;

    @Column(name = "COMPTEREMETTANT", length = 20)
    private String compteremettant;

    @Column(name = "ADRESSECLIENT", length = 70)
    private String adresseclient;

    @Column(name = "REFERENCEINTERNEREMISE", length = 42)
    private String referenceinterneremise;

    @Column(name = "PATHIMAGE", length = 128)
    private String pathimage;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATESAISIE")
    private Date datesaisie;
}