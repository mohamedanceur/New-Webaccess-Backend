
package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Entity
@Table(name = "EVENEMENT_AUTORISATION", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class EvenementsAutorisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEVENEMENT")
    private Integer idEven;

    @Column(name = "IDAUTORISATION")
    private Integer idAutorisation;

    @Column(name = "ACTION", length = 20)
    private String action;

    @Column(name = "CODEBANQUE", length = 10)
    private String codBanq;

    @Column(name = "CODEAGENCE", length = 20)
    private String codAgence;

    @Column(name = "NOM_UTILISATEUR", length = 50)
    private String nomUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEEVENEMENT")
    private Date dateEven;

    @Column(name = "IDUTILISATEUR", length = 2)
    private String idUser;

    @JdbcTypeCode(SqlTypes.NCHAR)
    @Column(name = "OLDDATE", length = 10)
    private String oldDate;

    @JdbcTypeCode(SqlTypes.NCHAR)
    @Column(name = "NEWDATE", length = 10)
    private String newDate;

    @Column(name = "IDPRELEVEMENT")
    private Integer idPrelevement;
}
