
package com.Atos.Webaccess.Domain.Agence;

import com.Atos.Webaccess.Domain.Banque.Banque;
import com.Atos.Webaccess.Domain.Groupes.Groupe;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AGENCES", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDAGENCE")
    private Integer id;

    @Column(name = "CODEAGENCE")
    private String code;

    @Column(name = "CODEPLACECOMPENSE")
    private String code_pc;

    @Column(name = "LIBELLEAGENCE")
    private String libelle;

    @Column(name = "STATUT")
    private Integer statut;

    @Column(name = "DATEMAJ")
    private Date datemaj;

    // Writable mapping of IDBANQUE
    @Column(name = "IDBANQUE")
    private Integer id_banque;

    // Read-only association using the same database column
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "IDBANQUE",
        insertable = false,
        updatable = false
    )
    private Banque bankAgence;

    // Groupe owns the many-to-many relationship
    @ManyToMany(mappedBy = "agencesGrp", fetch = FetchType.LAZY)
    private Set<Groupe> grps_agence = new HashSet<>();
}
