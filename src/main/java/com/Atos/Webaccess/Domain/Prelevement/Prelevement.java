package com.Atos.Webaccess.Domain.Prelevement;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "PRELEVEMENTS")
@Getter
@Setter
public class Prelevement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPRELEVEMENT")
    private Integer idprelevement;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DATEEXECUTION", length = 8)
    private String dateexecution;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DATEFACTURE", length = 8)
    private String datefacture;

    @Column(name = "DONNEESEMETTEUR", length = 70)
    private String donneesemetteur;

    @Column(name = "ETABLISSEMENT", length = 10)
    private String etablissement;

    @Column(name = "IDOPERATION")
    private Integer idoperation;

    @Column(name = "LIBELLE", length = 70)
    private String libelle;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "NUMEROEMETTEUR", length = 7)
    private String numeroemetteur;

    @Column(name = "IDAUTORISATION")
    private Integer idAutorisation;

    @Column(name = "MOTIFANNUL", length = 10)
    private String motifAnnul;

    @Column(name = "REFERENCEPRELEVEMENT", length = 20)
    private String reference;

    @Column(name = "RIBBENEFICIAIREINTERNE", length = 20)
    private String ribbeneficiaireInterne;
}

