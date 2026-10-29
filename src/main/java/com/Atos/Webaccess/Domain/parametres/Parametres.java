package com.Atos.Webaccess.Domain.parametres;


import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PARAMETRES", schema = "dbo")
@Getter
@Setter
@NoArgsConstructor
public class Parametres implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID_PARAMETRE")
    private Integer id;

    @Column(name = "TYPEPARAMETRE")
    private Integer type;

    @Column(name = "PARAMETRE")
    private String parametre;

    @Column(name = "VALEUR")
    private String valeur;

    @Column(name = "LAVERSION")
    private Integer laversion;

    @Column(name = "DATEMAJ")
    private Date datemaj;
}
