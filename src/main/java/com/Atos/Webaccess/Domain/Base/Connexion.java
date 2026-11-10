package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "CONNEXION", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Connexion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCONNEXION")
    private Integer id;

    @Column(name = "IDUTILISATEUR")
    private Integer idUtilisateur;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    private Date date;

    @Column(name = "STATUS")
    private Integer status;
}