
package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "PORTEURS", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Porteurs {

    @Id
    @Column(name = "NUMEROCARTE", length = 16)
    private String numerocarte;

    @Column(name = "MONTANTLIMITEDAB", length = 12)
    private String montantlimitedab;

    @Column(name = "NOMPORTE", length = 26)
    private String nomporte;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "CODENATIONALITE", length = 3)
    private String codenationalite;

    @Column(name = "PRENOM", length = 30)
    private String prenom;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "CODEEXISTANCE", length = 3)
    private String codeexistance;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SEXE", length = 3)
    private String sexe;

    @Column(name = "JOURNAISSANCE")
    private Integer journaissance;

    @Column(name = "TITRE", length = 5)
    private String titre;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DOCUMENTEXIGE", length = 3)
    private String documentexige;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "CODEPAYE", length = 3)
    private String codepaye;

    @Column(name = "NOMFAMILLE", length = 30)
    private String nomfamille;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SITUATION", length = 3)
    private String situation;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "TYPECARTE", length = 2)
    private String typecarte;

    @Column(name = "MONTANTLIMITETPE", length = 12)
    private String montantlimitetpe;

    @Column(name = "LIEN", length = 30)
    private String lien;

    @Column(name = "MONTANTTRANSACTIONS", length = 12)
    private String montanttransactions;

    @Column(name = "NUMEROCARTEPRIMAIRE", length = 16)
    private String numerocarteprimaire;

    @Column(name = "ADRESSE1", length = 40)
    private String adresse1;

    @Column(name = "NOMFAMILLEMERE", length = 25)
    private String nomfamillemere;

    @Column(name = "MONTANTLIMITETPEOFFLINE", length = 12)
    private String montantlimitetpeoffline;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "CODETYPEADRESSE", length = 3)
    private String codetypeadresse;

    @Column(name = "PROFESSION", length = 70)
    private String profession;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DATEFINVALIDITE", length = 8)
    private String datefinvalidite;

    @Column(name = "CODEPOSTALLE", length = 5)
    private String codepostalle;

    @Column(name = "NOMJEUNEFILLE", length = 30)
    private String nomjeunefille;

    @Column(name = "ADRESSE2", length = 20)
    private String adresse2;

    @Column(name = "RIB", length = 20)
    private String rib;

    @Column(name = "MOISNAISSANCE")
    private Integer moisnaissance;

    @Column(name = "NUMEROCONTRAT", length = 15)
    private String numerocontrat;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "ETATCARTE", length = 1)
    private String etatcarte;

    @Column(name = "NUMEROFAX", length = 15)
    private String numerofax;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "ADRESSE4", length = 3)
    private String adresse4;

    @Column(name = "ADRESSE3", length = 20)
    private String adresse3;

    @Column(name = "ANNEENAISSANCE")
    private Integer anneenaissance;

    @Column(name = "NOMSOCIETE", length = 30)
    private String nomsociete;

    @Column(name = "NUMEROTELEPHONE", length = 15)
    private String numerotelephone;

    @Column(name = "LIEUNAISSANCE", length = 30)
    private String lieunaissance;

    @Column(name = "ADRESSE5", length = 15)
    private String adresse5;

    @Column(name = "STATUT")
    private Integer statut;
}
