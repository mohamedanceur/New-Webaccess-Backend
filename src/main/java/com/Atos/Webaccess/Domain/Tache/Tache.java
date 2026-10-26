package com.Atos.Webaccess.Domain.Tache;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.Atos.Webaccess.User.Entities.User;

@Entity
@Table(name = "TACHES")
@Getter
@Setter
@NoArgsConstructor
public class Tache implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTACHE")
    private Integer id;

    @Column(name = "TYPETACHE")
    private String type;

    @Column(name = "TYPEINSTRUMENT")
    private String typeins;

    @Column(name = "NBMOTIFS")
    private Integer nbmotifs;

    @Column(name = "LIBELLE")
    private String libelle;

    @Column(name = "ETATSELECTION")
    private String etatselect;

    @Column(name = "ETATBASCULE1")
    private String etatbascule1;

    @Column(name = "ETATBASCULE2")
    private String etatbascule2;

    @Column(name = "ETATBASCULE3")
    private String etatbascule3;

    @Column(name = "ETATBASCULE4")
    private String etatbascule4;

    @Column(name = "ETATBASCULE5")
    private String etatbascule5;

    @Column(name = "ETATBASCULE6")
    private String etatbascule6;

    @Column(name = "ETATBASCULE7")
    private String etatbascule7;

    @Column(name = "ETATBASCULE8")
    private String etatbascule8;

    @Column(name = "ETATBASCULE9")
    private String etatbascule9;

    @Column(name = "ETATBASCULE10")
    private String etatbascule10;

    @Column(name = "LAVERSION")
    private Integer version;

    @Column(name = "STATUT")
    private Integer statut;

    @Column(name = "DATEMAJ")
    private Date datemaj;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "UTILISATEURS_TACHES",
        joinColumns = @JoinColumn(name = "IDTACHE"),
        inverseJoinColumns = @JoinColumn(
            name = "IDUTILISATEUR",
            referencedColumnName = "id"
        )
    )
    private Set<User> usersTache = new HashSet<>();
}
