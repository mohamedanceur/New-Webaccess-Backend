
package com.Atos.Webaccess.Domain.SystemeBancaire;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "SYSTEMEBANCAIRES")
@Getter
@Setter
public class SystemeBancaire {

    @Id
    @Column(name = "IDSYSTEMEBANCAIRE")
    private Integer idsystemebancaire;

    @Column(name = "TAILLECODEBANQUE", precision = 38, scale = 0)
    private BigDecimal taillecodebanque;

    @Column(name = "TAILLECODEAGENCE")
    private BigDecimal taillecodeagence;

    @Column(name = "TAILLENUMEROCOMPTE")
    private BigDecimal taillenumerocompte;

    @Column(name = "TAILLECLERIB")
    private BigDecimal tailleclerib;

    @Column(name = "CODEDEVISE")
    private String codedevise;

    @Column(name = "TAILLEMONTANT")
    private BigDecimal taillemontant;

    @Column(name = "TAILLENUMEROCHEQUE")
    private BigDecimal taillenumerocheque;

    @Column(name = "DELAIREGLEMENT")
    private BigDecimal delaireglement;

    @Column(name = "POSCODEBANQUERIB")
    private BigDecimal poscodebanquerib;

    @Column(name = "POSCODEAGENCERIB")
    private BigDecimal poscodeagencerib;

    @Column(name = "POSNUMEROCOMPTERIB")
    private BigDecimal posnumerocompterib;

    @Column(name = "POSCLERIBRIB")
    private BigDecimal poscleribrib;

    @Column(name = "TAILLERIB")
    private BigDecimal taillerib;

    @Column(name = "TAILLERIO")
    private BigDecimal taillerio;

    @Column(name = "TAILLEIIO")
    private BigDecimal tailleiio;

    @Column(name = "TYPENUMEROCOMPTE")
    private String typenumerocompte;
}
