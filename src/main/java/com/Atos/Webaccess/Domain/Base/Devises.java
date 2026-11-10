package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "DEVISES", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Devises {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDDEVISE")
    private Integer iddevise;

    @Column(name = "NOMBREDECIMALE")
    private Integer nombredecimale;

    @Column(name = "CODEDEVISE", length = 3)
    private String codedevise;

    @Column(name = "LAVERSION")
    private Integer laversion;

    @Column(name = "LIBELLEDEVISE", length = 70)
    private String libelledevise;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEMAJ")
    private Date datemaj;
}