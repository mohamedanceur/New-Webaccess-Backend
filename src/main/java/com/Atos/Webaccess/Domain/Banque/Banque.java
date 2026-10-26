package com.Atos.Webaccess.Domain.Banque;



import com.Atos.Webaccess.Domain.Agence.Agence;
import com.Atos.Webaccess.User.Entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity
@Table(name = "BANQUES")
@Getter
@Setter
@NoArgsConstructor
public class Banque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDBANQUE")
    private Integer id;

    @Column(name = "CODEBANQUE")
    private String code;

    @Column(name = "IDPAYS")
    private Integer idPays;

    @Column(name = "LIBELLEBANQUE")
    private String libelle;

    @Column(name = "CODEBIN")
    private Integer codeBin;

    @Column(name = "ALGORITHMEDECONTROLESPECIFIQUE")
    private Integer algo;

    @Column(name = "BANQUEMERE")
    private Integer banqueMere;

    @Column(name = "STATUT")
    private Integer statut;

    @Column(name = "DATEMAJ")
    private Date datemaj;

    /*
     * Inverse relationships: the foreign keys remain on USER
     * and AGENCES. These collections do not create join tables.
     */
    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY)
    private Set<User> usersBanque = new HashSet<>();

    @OneToMany(mappedBy = "bankAgence", fetch = FetchType.LAZY)
    private Set<Agence> agencesBanque = new HashSet<>();
}
