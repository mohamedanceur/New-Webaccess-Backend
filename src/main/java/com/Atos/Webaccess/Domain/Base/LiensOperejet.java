package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "LIENS_OPEREJET", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class LiensOperejet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDOPERATIONREJETEE")
    private Integer idoperationrejetee;

    @Column(name = "IDOPERATIONREJET", nullable = false)
    private Integer idoperationrejet;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "MOTIF_DE_REJET", length = 5)
    private String motifDeRejet;  

    @Column(name = "STATUT")
    private Integer statut;
}