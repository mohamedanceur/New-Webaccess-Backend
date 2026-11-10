package com.Atos.Webaccess.Domain.PMobiles;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "PMOBILES", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class PMobiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPMOBILE")
    private Integer idPMobile;

    @Column(name = "ETABLISSEMENT", length = 10)
    private String etablissement;

    @Column(name = "IDOPERATION", nullable = false)
    private Integer idoperation;

    @Column(name = "MONTANT", length = 15)
    private String montant;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SENSOPERATION", length = 1)
    private String sensoperation;
    
    @Column(name = "MONTANTCOMISSION", length = 15)
    private String montantcomission;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "SENSMONTANTCOMISSION", length = 1)
    private String sensmontantcomission;
    
    @Column(name = "MONTANTCOMPENSATION", length = 7)
    private String montantcompensation;
    
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DATEREMISE", length = 8)
    private String dateremise;

    @Column(name = "TYPEOPERATIONPAIEMENTMOBILE", length = 2)
    private String typepaiement;

    @Column(name = "NUMAUTORISATIONIPMOBILE", length = 15)
    private String numeroautorisation;

    @Column(name = "IDPLATEFORMEPAIEMENTTIRE", length = 20)
    private String idplatformepaiementtire;

    @Column(name = "IDPLATEFORMEPAIEMENTTIREUR", length = 20)
    private String idplatformepaiementtireur;

    @Column(name = "REFERENCETRANSACTION", length = 12)
    private String referencetransaction;

    @Column(name = "LIBELLE", length = 70)
    private String libelle;
}