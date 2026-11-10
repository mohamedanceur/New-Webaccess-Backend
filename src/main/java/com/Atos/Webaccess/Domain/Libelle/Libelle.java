
package com.Atos.Webaccess.Domain.Libelle;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LIBELLES")
@Getter
@Setter
public class Libelle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDLIBELLE")
    private Integer id;

    @Column(name = "TYPELIBELLE")
    private String type;

    @Column(name = "CODE")
    private String code;

    @Column(name = "LIBELLE1")
    private String libelle1;

    @Column(name = "LIBELLE2")
    private String libelle2;

    @Column(name = "LIBELLE3")
    private String libelle3;

    @Column(name = "LAVERSION")
    private Integer version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEMAJ")
    private Date datemaj;
}
