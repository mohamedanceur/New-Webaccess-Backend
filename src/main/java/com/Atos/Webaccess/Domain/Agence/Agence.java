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
@Table(name = "AGENCES")
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

    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEMAJ")
    private Date datemaj;

    // This field maps the existing IDBANQUE column.
    @Column(name = "IDBANQUE")
    private Integer id_banque;

    // Read-only association: id_banque remains the writable field,
    // matching the legacy Hibernate mapping.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "IDBANQUE",
        insertable = false,
        updatable = false
    )
    private Banque bankAgence;

    // The Groupe entity owns the GROUPE_AGENCES relationship.
    @ManyToMany(mappedBy = "agences_grp", fetch = FetchType.LAZY)
    private Set<Groupe> grps_agence = new HashSet<>();
}
