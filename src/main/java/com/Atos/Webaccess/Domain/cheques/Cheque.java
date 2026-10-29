
package com.Atos.Webaccess.Domain.cheques;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cheques", schema = "dbo")
public class Cheque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCHEQUE")
    private Integer idcheque;

    @Column(name = "ETATCHEQUE")
    private Integer etatcheque;

    @Column(name = "RIBTIRE", length = 40)
    private String ribtire;

    @Column(name = "NOM_ORDINATEUR", length = 50)
    private String nomOrdinateur;

    @Column(name = "NUMEROCHEQUE", length = 10)
    private String numerocheque;

    @Column(name = "AGENCEREMETTANT", length = 20)
    private String agenceremettant;

    @Column(name = "FICHIERIMAGE", length = 64)
    private String fichierimage;

    @Column(name = "IDIMAGEUAP")
    private Integer idimageuap;

    @Column(name = "DEVISE", columnDefinition = "char(3)")
    private String devise;

    @Column(name = "NOM_UTILISATEUR", length = 50)
    private String nomUtilisateur;

    @Column(name = "NUMCOMPTETIRE", length = 20)
    private String numcomptetire;

    @Column(name = "BANQUEREMETTANT", length = 10)
    private String banqueremettant;

    @Column(name = "IDFICHIERIMGIN")
    private Integer idfichierimgin;

    @Column(name = "MONTANT", length = 20)
    private String montant;

    @Column(name = "BANQUETIRE", length = 10)
    private String banquetire;

    @Column(name = "ARCHIVE", length = 128)
    private String archive;

    @Column(name = "TYPEDECHEQUE", columnDefinition = "char(1)")
    private String typeDeCheque;

    @Column(name = "ETABLISSEMENT", length = 10)
    private String etablissement;

    @Column(name = "DATEEMISSION")
    private Date dateemission;

    @Column(name = "AGENCETIRE", length = 20)
    private String agencetire;

    @Column(name = "CORRECTIONPISTEOCRCMC7")
    private Integer correctionpisteocrcmc7;

    @Column(name = "ADRESSETIRE", length = 70)
    private String adressetire;

    @Column(name = "NOMTIRE", length = 50)
    private String nomtire;

    @Column(name = "PATHIMAGE", length = 128)
    private String pathimage;

    public Cheque() {
    }

}
