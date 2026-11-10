package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LIENS_OPEANNULATION", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class LiensOpeannulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDOPERATIONANNULEE")
    private Integer idoperationannulee;

    @Column(name = "AGENCEINITIATRICE", length = 20)
    private String agenceinitiatrice;

    @Column(name = "IDOPERATIONANNULATION", nullable = false)
    private Integer idoperationannulation;

    @Column(name = "STATUT")
    private Integer statut;
}