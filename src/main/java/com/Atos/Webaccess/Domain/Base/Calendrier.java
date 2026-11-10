package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "CALENDRIER", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Calendrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCALENDRIER")
    private Integer idcalendrier;

    @Column(name = "DATECALENDRIER", nullable = true)
    private Date datecalendrier;

    @Column(name = "TYPEDATE", length = 20, nullable = true)
    private String typedate;
}