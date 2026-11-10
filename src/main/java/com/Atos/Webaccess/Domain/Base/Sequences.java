
package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "sequences", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Sequences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDSEQUENCE")
    private Integer idsequence;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "DATEDEVALEUR", length = 8)
    private String datedevaleur;

    @Column(name = "TYPESEQUENCE")
    private String typesequence;

    @Column(name = "SEQUENCE")
    private Integer sequence;

    @Column(name = "DATEDEVALEUR", insertable = false, updatable = false)
    private String datevaleur;

    @Column(name = "MODE")
    private String mode;
}
