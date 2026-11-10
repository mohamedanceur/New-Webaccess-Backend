package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CORRESPONDANCES", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Correspondances {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MOTIFSIB")
    private String motifsib;

    @Column(name = "MOTIFREJET")
    private String motifrejet;

    @Column(name = "REJETAVALELLIGIBLE")
    private Integer rejetAvalElligible;
}