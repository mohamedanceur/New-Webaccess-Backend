package com.Atos.Webaccess.Domain.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PLAFONDS", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Plafonds {

    @Id
    @Column(name = "PLAFONDS", length = 5)
    private String plafonds;

    @Column(name = "LIMITE2", length = 6)
    private String limite2;

    @Column(name = "LIMITE1", length = 6)
    private String limite1;

    @Column(name = "STATUT")
    private Integer statut;
}