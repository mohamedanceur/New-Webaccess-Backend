package com.Atos.Webaccess.Domain.Groupes;


import com.Atos.Webaccess.Domain.Agence.Agence;
import com.Atos.Webaccess.User.Entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GROUPES")
@Getter
@Setter
@NoArgsConstructor
public class Groupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDGROUPE")
    private Integer id;

    @Column(name = "LIBELLE")
    private String libelle;

    @Column(name = "LIBELLEAGENCE")
    private String libelleAgence;

    @Column(name = "STATUT")
    private Integer statut;

    /*
     * Legacy mapping: GROUPES owns the IDGROUPE foreign-key
     * relationship with users.
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDGROUPE")
    private Set<User> usersGroup = new HashSet<>();

    /*
     * Groupe owns the many-to-many relationship.
     * This reuses the existing join table.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "GROUPE_AGENCES",
        joinColumns = @JoinColumn(name = "IDGROUPE"),
        inverseJoinColumns = @JoinColumn(name = "IDAGENCE")
    )
    private Set<Agence> agencesGrp = new HashSet<>();

    public void addAgence(Agence agence) {
        agencesGrp.add(agence);
        agence.getGrps_agence().add(this);
    }

    public void removeAgence(Agence agence) {
        agencesGrp.remove(agence);
        agence.getGrps_agence().remove(this);
    }
}
