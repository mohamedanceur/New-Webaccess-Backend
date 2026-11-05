package com.Atos.Webaccess.Domain.effets;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "OP_EFFET_EN_ATTENTE")
@Getter
@Setter
public class OpEffetEnAttente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDOPERATION")
    private Integer idoperation;

    @Column(name = "IDINSTRUMENT")
    private Integer idinstrument;

    @Column(name = "NUMCOMPTEBENEFICIAIRE", length = 20)
    private String numcomptebeneficiaire;

    @Column(name = "IDLOTINTI")
    private Integer idlotinti;

    @Column(name = "IDOPERATIONREJET")
    private Integer idoperationrejet;

    @Column(name = "AGENCEREMETTANTE", length = 20)
    private String agenceremettante;

    @Column(name = "IDLOTINTO")
    private Integer idlotinto;

    @Column(name = "DATETRAITEMENT")
    private LocalDateTime datetraitement;

    @Column(name = "DEVISE", length = 3)
    private String devise;

    @Column(name = "NUMCOMPTETIRE", length = 20)
    private String numcomptetire;

    @Column(name = "BANQUETIRE", length = 10)
    private String banquetire;

    @Column(name = "CODEPLACE", length = 2)
    private String codeplace;

    @Column(name = "AGENCEBENEFICIAIRE", length = 20)
    private String agencebeneficiaire;

    @Column(name = "ETABLISSEMENT", length = 10)
    private String etablissement;

    @Column(name = "AGENCETIRE", length = 20)
    private String agencetire;

    @Column(name = "ADRESSETIRE", length = 70)
    private String adressetire;

    @Column(name = "LIBELLEOPERATION", length = 70)
    private String libelleoperation;

    @Column(name = "RIBBENEFICIAIRE", length = 40)
    private String ribbeneficiaire;

    @Column(name = "IBANBENEFICIAIRE", length = 4)
    private String ibanbeneficiaire;

    @Column(name = "DATESAISIE")
    private LocalDateTime datesaisie;

    @Column(name = "REFERENCEINTERNE", length = 42)
    private String referenceinterne;

    @Column(name = "DATEPRESENTATION")
    private LocalDateTime datepresentation;

    @Column(name = "MONTANTOPERATION", length = 20)
    private String montantoperation;

    @Column(name = "IDFICHIERIMGOUT")
    private Integer idfichierimgout;

    @Column(name = "RIBTIRE", length = 40)
    private String ribtire;

    @Column(name = "NOMBENEFICIAIRE", length = 50)
    private String nombeneficiaire;

    @Column(name = "ETAT")
    private Integer etat;

    @Column(name = "BANQUEBENEFICIAIRE", length = 10)
    private String banquebeneficiaire;

    @Column(name = "MOTIFREJET", length = 5)
    private String motifrejet;

    @Column(name = "IDLOTUAP")
    private Integer idlotuap;

    @Column(name = "IDIMAGEUAP")
    private Integer idimageuap;

    @Column(name = "ADRESSEBENEFICIAIRE", length = 70)
    private String adressebeneficiaire;

    @Column(name = "ORIGINE")
    private Integer origine;

    @Column(name = "IBANTIRE", length = 4)
    private String ibantire;

    @Column(name = "RIO", length = 50)
    private String rio;

    @Column(name = "TYPEOPERATION", length = 3)
    private String typeoperation;

    @Column(name = "IDFICHIERIMGIN")
    private Integer idfichierimgin;

    @Column(name = "DATEREGLEMENT")
    private LocalDateTime datereglement;

    @Column(name = "DATEESCOMPTE")
    private LocalDateTime dateescompte;

    @Column(name = "IDREMISE")
    private Integer idremise;

    @Column(name = "NOMTIRE", length = 50)
    private String nomtire;

}
