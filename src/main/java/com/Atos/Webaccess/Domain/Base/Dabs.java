package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DABS", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Dabs {

    @Id
    @Column(name = "IDENTIFIANTDAB")
    private String identifiantdab;

    @Column(name = "CODEAGENCE", length = 10)
    private String codeagence;

    @Column(name = "STATUT")
    private Integer statut;
}