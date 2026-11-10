package com.Atos.Webaccess.Domain.Lots;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Table(name = "lots", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Lots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDLOT")
    private Integer idlot;

    @Column(name = "AGENCEDESTINATAIRE", length = 20)
    private String agencedestinataire;

    @Column(name = "NOM_ORDINATEUR", length = 50)
    private String nomOrdinateur;

    @Column(name = "ETAT")
    private Integer etat;

    @Column(name = "DEVISE", length = 3)
    private String devise;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATETRAITEMENT")
    private Date datetraitement;

    @Column(name = "IDFICHIERCRO")
    private Integer idfichiercro;

    @Column(name = "NOM_UTILISATEUR", length = 50)
    private String nomUtilisateur;

    @Column(name = "IDFICHIERLOT")
    private Integer idfichierlot;

    @Column(name = "ORIGINE")
    private Integer origine;

    @Column(name = "IDFICHIERCRI")
    private Integer idfichiercri;

    @Column(name = "MONTANT")
    private String montant;

    @Column(name = "NOMBREOPERATION")
    private Integer nombreoperation;

    @Column(name = "NOMBREREMISE")
    private Integer nombreremise;

    @Column(name = "IDORDREUAP")
    private Integer idordreuap;

    @Column(name = "NUMSEQUENCE", length = 12)
    private String numsequence;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATECREATION")
    private Date datecreation;

    @Column(name = "AGENCEEMETTRICE", length = 20)
    private String agenceemettrice;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SOURCE", length = 3)
    private String source;

    @Column(name = "TYPELOT", length = 3)
    private String typelot;

    @Column(name = "IDFICHIERCRL")
    private Integer idfichiercrl;

    @Column(name = "BANQUEDESTINATAIRE", length = 10)
    private String banquedestinataire;

    @Column(name = "BANQUEEMETTRICE", length = 10)
    private String banqueemettrice;
}