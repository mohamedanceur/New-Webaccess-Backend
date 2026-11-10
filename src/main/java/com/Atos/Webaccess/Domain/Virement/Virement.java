package com.Atos.Webaccess.Domain.Virement;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "VIREMENTS")
@Getter
@Setter
public class Virement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDVIREMENT")
    private Integer idvirement;

    
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "NATUREDESFONDS", length = 1)
    private String naturedesfonds;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "NUMEROINTERNE", length = 10)
    private String numerointerne;
    
    @Column(name = "MONTANTFRAIS1", length = 20)
    private String montantfrais1;

    @Column(name = "DEVISEINITIALE", length = 3)
    private String deviseinitiale;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "MOTIFREGULARISATION", length = 5)
    private String motifregularisation;

    @Column(name = "DEVISEFRAIS3", length = 3)
    private String devisefrais3;

    @Column(name = "DEVISEFRAIS1", length = 3)
    private String devisefrais1;

    @Column(name = "IDOPERATION")
    private Integer idoperation;

    @Column(name = "MONTANTINITIAL", length = 20)
    private String montantinitial;

    @Column(name = "MONTANTFRAIS2", length = 20)
    private String montantfrais2;


    @Column(name = "RIO_REGULARISEE", length = 50)
    private String rio_regularisee;


    @Column(name = "DEVISEFRAIS4", length = 3)
    private String devisefrais4;

    @Column(name = "MONTANTFRAIS3", length = 20)
    private String montantfrais3;

    @Column(name = "REFERENCEINITIALE", length = 20)
    private String referenceinitiale;

    @Column(name = "DEVISEFRAIS2", length = 3)
    private String devisefrais2;

    // Preserve the legacy database column spelling.
    @Column(name = "ETABLISSEMENET", length = 10)
    private String etablissemenet;

    @Column(name = "MONTANTFRAIS4", length = 20)
    private String montantfrais4;
}
