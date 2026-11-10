
package com.Atos.Webaccess.Domain.Carte;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "OPCARTES")
@Getter
@Setter
public class Carte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDOPCARTE")
    private Integer idopcarte;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "BIN", length = 6)
    private String bin;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SYSTEMEACQUEREUR", length = 10)
    private String systemeacquereur;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SENSOPERATION", length = 1)
    private String sensoperation;

    // SQL Server: VARCHAR(12)
    @Column(name = "NUMEROAUTORISATION", length = 12)
    private String numeroautorisation;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "TYPECOMMISSION", length = 1)
    private String typecommission;

    @Column(name = "IDOPERATION", nullable = false)
    private Integer idoperation;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "POINTACQUEREUR", length = 10)
    private String pointacquereur;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "CODEACTIVITE", length = 6)
    private String codeactivite;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "NUMEROTRANSACTION", length = 12)
    private String numerotransaction;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "MODELECTURECARTE", length = 1)
    private String modelecturecarte;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DATEDEBUTVALIDITEAPPLICATION", length = 8)
    private String datedebutvaliditeapplication;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "MODEAUTHENTIFICATION", length = 1)
    private String modeauthentification;

    // SQL Server: VARCHAR(20)
    @Column(name = "MONTANT", length = 20)
    private String montant;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "NUMEROCARTE", length = 16)
    private String numerocarte;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "NUMEROCONTRATACCEPTEUR", length = 15)
    private String numerocontrataccepteur;

    // SQL Server: VARCHAR(10)
    @Column(name = "ETABLISSEMENT", length = 10)
    private String etablissement;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DATEFINVALIDITEAPPLICATION", length = 8)
    private String datefinvaliditeapplication;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DONNEESEMV", length = 8)
    private String donneesemv;
       
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DATEREMISE", length = 8)
    private String dateremise;

    // SQL Server: VARCHAR(20)
    @Column(name = "COMISSION", length = 20)
    private String comission;

    // SQL Server: VARCHAR(12)
    @Column(name = "TELEPHONECOMMERCANT", length = 12)
    private String telephonecommercant;

    // SQL Server: VARCHAR(20)
    @Column(name = "TYPEPAIEMENT", length = 20)
    private String typepaiement;

    // SQL Server: VARCHAR(3)
    @Column(name = "TYPEOPERATIONCARTE", length = 3)
    private String typeoperationcarte;
}
