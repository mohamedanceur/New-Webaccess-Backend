
package com.Atos.Webaccess.Domain.cheques;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "CHEQUES")
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

    @Column(name = "DEVISE", length = 3)
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

    @Column(name = "TYPEDECHEQUE", length = 1)
    private String typedecheque;

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

    // Getters and setters

    public Integer getIdcheque() {
        return idcheque;
    }

    public void setIdcheque(Integer idcheque) {
        this.idcheque = idcheque;
    }

    public Integer getEtatcheque() {
        return etatcheque;
    }

    public void setEtatcheque(Integer etatcheque) {
        this.etatcheque = etatcheque;
    }

    public String getRibtire() {
        return ribtire;
    }

    public void setRibtire(String ribtire) {
        this.ribtire = ribtire;
    }

    public String getNomOrdinateur() {
        return nomOrdinateur;
    }

    public void setNomOrdinateur(String nomOrdinateur) {
        this.nomOrdinateur = nomOrdinateur;
    }

    public String getNumerocheque() {
        return numerocheque;
    }

    public void setNumerocheque(String numerocheque) {
        this.numerocheque = numerocheque;
    }

    public String getAgenceremettant() {
        return agenceremettant;
    }

    public void setAgenceremettant(String agenceremettant) {
        this.agenceremettant = agenceremettant;
    }

    public String getFichierimage() {
        return fichierimage;
    }

    public void setFichierimage(String fichierimage) {
        this.fichierimage = fichierimage;
    }

    public Integer getIdimageuap() {
        return idimageuap;
    }

    public void setIdimageuap(Integer idimageuap) {
        this.idimageuap = idimageuap;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getNumcomptetire() {
        return numcomptetire;
    }

    public void setNumcomptetire(String numcomptetire) {
        this.numcomptetire = numcomptetire;
    }

    public String getBanqueremettant() {
        return banqueremettant;
    }

    public void setBanqueremettant(String banqueremettant) {
        this.banqueremettant = banqueremettant;
    }

    public Integer getIdfichierimgin() {
        return idfichierimgin;
    }

    public void setIdfichierimgin(Integer idfichierimgin) {
        this.idfichierimgin = idfichierimgin;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getBanquetire() {
        return banquetire;
    }

    public void setBanquetire(String banquetire) {
        this.banquetire = banquetire;
    }

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    public String getTypedecheque() {
        return typedecheque;
    }

    public void setTypedecheque(String typedecheque) {
        this.typedecheque = typedecheque;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public Date getDateemission() {
        return dateemission;
    }

    public void setDateemission(Date dateemission) {
        this.dateemission = dateemission;
    }

    public String getAgencetire() {
        return agencetire;
    }

    public void setAgencetire(String agencetire) {
        this.agencetire = agencetire;
    }

    public Integer getCorrectionpisteocrcmc7() {
        return correctionpisteocrcmc7;
    }

    public void setCorrectionpisteocrcmc7(Integer correctionpisteocrcmc7) {
        this.correctionpisteocrcmc7 = correctionpisteocrcmc7;
    }

    public String getAdressetire() {
        return adressetire;
    }

    public void setAdressetire(String adressetire) {
        this.adressetire = adressetire;
    }

    public String getNomtire() {
        return nomtire;
    }

    public void setNomtire(String nomtire) {
        this.nomtire = nomtire;
    }

    public String getPathimage() {
        return pathimage;
    }

    public void setPathimage(String pathimage) {
        this.pathimage = pathimage;
    }
}
