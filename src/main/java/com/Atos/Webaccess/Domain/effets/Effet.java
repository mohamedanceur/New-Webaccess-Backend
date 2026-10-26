package com.Atos.Webaccess.Domain.effets;



import java.util.Date;

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
@Table(name = "EFFETS")
@Getter
@Setter
@NoArgsConstructor
public class Effet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDEFFET")
    private Integer ideffet;

    @Column(name = "NOMTIREUR", length = 50)
    private String nomtireur;

    @Column(name = "NUMEROEFFET", length = 10)
    private String numeroeffet;

    @Column(name = "DATEECHEANCE")
    private Date dateecheance;

    @Column(name = "MONTANTFRAIS", length = 20)
    private String montantfrais;

    @Column(name = "AGENCEINITIATRICEORDRE", length = 20)
    private String agenceinitiatriceordre;

    @Column(name = "TYPEEFFET", length = 3)
    private String typeeffet;

    @Column(name = "CODEENDOSSEMENT", length = 32)
    private String codeendossement;

    @Column(name = "MONTANTBRUT", length = 20)
    private String montantbrut;

    @Column(name = "CODEAVAL", length = 32)
    private String codeaval;

    @Column(name = "ETABLISSEMENT", length = 10)
    private String etablissement;

    @Column(name = "IBANTIREUR", length = 4)
    private String ibantireur;

    @Column(name = "RIBTIREUR", length = 40)
    private String ribtireur;

    @Column(name = "ADRESSETIREUR", length = 70)
    private String adressetireur;

    @Column(name = "PATHIMAGE", length = 128)
    private String pathimage;

    @Column(name = "FICHIERIMAGE", length = 64)
    private String fichierimage;
}
