package com.Atos.Webaccess.Domain.Evenement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Table(name = "EVENEMENTS")
@Getter
@Setter
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEVENEMENT")
    private Integer idEven;

    @Column(name = "ETAT")
    private Integer etatEven;

    @Column(name = "IDOPERATION")
    private Integer idOper;

    @Column(name = "APPLICATION")
    private String applic;

    @Column(name = "CODEBANQUE")
    private String codBanq;

    @Column(name = "CODEAGENCE")
    private String codAgence;

    @Column(name = "POSTE")
    private String poste;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "TYPEEVENEMENT", length = 3)
    private String typEven;

    @Column(name = "NOM_UTILISATEUR")
    private String nomUser;

    @Column(name = "NOM_ORDINATEUR")
    private String nomOrdi;

    @Column(name = "ETATINITIAL")
    private Integer etatI;

    @Column(name = "ETATFINAL")
    private Integer etatF;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEEVENEMENT")
    private Date dateEven;

    @Column(name = "IDFICHIER")
    private Integer idFichier;

    @Column(name = "IDUTILISATEUR")
    private String idUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEORIGINEEVENEMENT")
    private Date dateOrigine;

    @Column(name = "IDFICHIEREVO")
    private Integer idFichierEvo;
}