
package com.Atos.Webaccess.Domain.ChargeBack;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "CHARGEBACKS")
@Getter
@Setter
public class ChargeBack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCHARGEBACK")
    private Integer idchargeback;

    @Column(name = "ETABLISSEMENT")
    private String etablissement;

    @Column(name = "IDOPERATION")
    private Integer idoperation;

    @Column(name = "TYPEOPERATIONCHARGEBACK")
    private String typeoperationchargeback;

    @Column(name = "MONTANT")
    private String montant;

    @Column(name = "REFERENCE")
    private String reference;

    // Preserve the legacy database spelling: BENIFICAIRE
    @Column(name = "CODEBANQUEBENIFICAIRE")
    private String codebanquebenificaire;

    @Column(name = "CODEAGENCEBENIFICAIRE")
    private String codeagencebenificaire;


    @Column(name = "CODEBANQUETIRE")
    private String codebanquetire;

    @Column(name = "CODEAGENCETIRE")
    private String codeagencetire;

    @Column(name = "CODEMOTIF")
    private String codemotif;

    @Column(name = "IDENTIFIANT")
    private String identifiant;


    @Column(name = "RIBPORTEUR")
    private String ribporteur;


    @Column(name = "NUMEROAUTORISATION")
    private String numeroautorisation;
    
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DATECHARGEBACK", length = 8)
    private String datechargeback;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SENSMONTANT", length = 2)
    private String sensmontant;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DATEOPERATIONINITIALE", length = 8)
    private String dateoperationinitiale;
}
