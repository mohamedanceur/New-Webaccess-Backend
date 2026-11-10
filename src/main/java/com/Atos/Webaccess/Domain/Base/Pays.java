package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Table(name = "PAYS", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Pays {

    @Id
    @Column(name = "IDPAYS")
    private Integer id;
    
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "CODEPAYSISO", length = 2)
    private String codepaysiso;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "CODEPAYSNUMERIQUE", length = 2)
    private String codepaysnumerique;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "CODEPAYSISONUMERIQUE", length = 4)
    private String codepaysisonumerique;

    @Column(name = "NOMPAYS")
    private String nom;

    @Column(name = "DATEMAJ")
    private Date datemaj;
    
    @Column(name = "LAVERSION")
    private Integer laversion;
}