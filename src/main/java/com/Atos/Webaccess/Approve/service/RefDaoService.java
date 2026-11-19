package com.Atos.Webaccess.Approve.service;

import com.Atos.Webaccess.Domain.Base.*;
import com.Atos.Webaccess.Domain.Banque.Banque;
import com.Atos.Webaccess.Domain.Banque.BanqueRepository;
import com.Atos.Webaccess.Domain.Libelle.Libelle;
import com.Atos.Webaccess.Domain.Libelle.LibelleRepository;
import com.Atos.Webaccess.Domain.SystemeBancaire.SystemeBancaire;
import com.Atos.Webaccess.Domain.SystemeBancaire.SystemeBancaireRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class RefDaoService {

    private final PaysRepository paysRepository;
    private final BanqueRepository banqueRepository;
    private final LibelleRepository libelleRepository;
    private final PlafondsRepository plafondsRepository;
    private final SystemeBancaireRepository systemeBancaireRepository;
    private final CalendrierRepository calendrierRepository;

    public RefDaoService(
            PaysRepository paysRepository,
            BanqueRepository banqueRepository,
            LibelleRepository libelleRepository,
            PlafondsRepository plafondsRepository,
            SystemeBancaireRepository systemeBancaireRepository,
            CalendrierRepository calendrierRepository) {

        this.paysRepository = paysRepository;
        this.banqueRepository = banqueRepository;
        this.libelleRepository = libelleRepository;
        this.plafondsRepository = plafondsRepository;
        this.systemeBancaireRepository = systemeBancaireRepository;
        this.calendrierRepository = calendrierRepository;
    }

    public List<Pays> getAllPays() {
        return paysRepository.findAllByOrderByIdAsc();
    }

    public List<Banque> getAllBanque() {
        return banqueRepository.findByStatutOrderByLibelleAsc(1);
    }

    public List<Libelle> getListCodeOperation() {
        return libelleRepository.findByTypeOrderByCodeAsc("CODEOPERATION");
    }

    public List<Plafonds> getPlafonds() {
        return plafondsRepository.findAllByOrderByLimite1Asc();
    }

    public Map<String, Object> systemBancaire() {

        Map<String, Object> result = new HashMap<>();

        SystemeBancaire systeme = systemeBancaireRepository
                .findByIdsystemebancaire(1)
                .orElse(null);

        if (systeme == null) {
            applyDefaultSystemParameters(result);
        } else {
            int tailleCodeBanque = valueOrDefault(
                    systeme.getTaillecodebanque(), 3);

            int tailleCodeAgence = valueOrDefault(
                    systeme.getTaillecodeagence(), 5);

            result.put("taillecodebanque", String.valueOf(tailleCodeBanque));
            result.put("taillecodeagence", String.valueOf(tailleCodeAgence));
            result.put("tailleFinaleCodeAgence",
                    String.valueOf(tailleCodeBanque + tailleCodeAgence));

            result.put("taillenumerocompte",
                    String.valueOf(valueOrDefault(
                            systeme.getTaillenumerocompte(), 10)));

            result.put("tailleclerib",
                    String.valueOf(valueOrDefault(
                            systeme.getTailleclerib(), 2)));

            result.put("codedevise",
                    systeme.getCodedevise() == null
                            ? "DZD"
                            : systeme.getCodedevise());

            result.put("taillemontant",
                    valueOrDefault(systeme.getTaillemontant(), 15));

            result.put("taillenumerocheque",
                    valueOrDefault(systeme.getTaillenumerocheque(), 15));

            result.put("delaireglement",
                    valueOrDefault(systeme.getDelaireglement(), 1));

            result.put("poscodebanquerib",
                    valueOrDefault(systeme.getPoscodebanquerib(), 0));

            result.put("poscodeagencerib",
                    valueOrDefault(systeme.getPoscodeagencerib(), 3));

            result.put("posnumerocompterib",
                    valueOrDefault(systeme.getPosnumerocompterib(), 8));

            result.put("poscleribrib",
                    valueOrDefault(systeme.getPoscleribrib(), 18));

            result.put("taillerib",
                    valueOrDefault(systeme.getTaillerib(), 20));

            result.put("taillerio",
                    valueOrDefault(systeme.getTaillerio(), 38));

            result.put("tailleiio",
                    valueOrDefault(systeme.getTailleiio(), 42));

            result.put("typenumerocompte",
                    systeme.getTypenumerocompte() == null
                            ? "A"
                            : systeme.getTypenumerocompte());
        }

        // Parameters defined directly by the legacy application.
        result.put("taillenumerocarte", "16");
        result.put("taillenumeroeffet", "7");
        result.put("taillenumerointerne", "10");
        result.put("taillerefinit", "16");
        result.put("tailletelephone", "10");
        result.put("motif_rejet_default", "MC000");
        result.put("taillenumeroprelevement", "7");
        result.put("tailledonneesemetteur", "17");

        return result;
    }

    private void applyDefaultSystemParameters(Map<String, Object> result) {

        result.put("taillecodebanque", "3");
        result.put("taillecodeagence", "5");
        result.put("tailleFinaleCodeAgence", "8");
        result.put("taillenumerocompte", "10");
        result.put("tailleclerib", "2");
        result.put("codedevise", "DZD");
        result.put("taillemontant", 15);
        result.put("taillenumerocheque", 7);
        result.put("delaireglement", 3);

        result.put("poscodebanquerib", 0);
        result.put("poscodeagencerib", 3);
        result.put("posnumerocompterib", 8);
        result.put("poscleribrib", 18);

        result.put("taillerib", 20);
        result.put("taillerio", 38);
        result.put("tailleiio", 42);
        result.put("typenumerocompte", "A");
    }

    private int valueOrDefault(BigDecimal value, int defaultValue) {
        return value == null ? defaultValue : value.intValue();
    }

    public int[] getJoursWeekEnd() {

        int weekend1 = 0;
        int weekend2 = 0;

        List<Calendrier> weekends = calendrierRepository
                .findByTypedateIn(
                        Arrays.asList("WeekEnd1", "WeekEnd2"));

        for (Calendrier calendrier : weekends) {

            Date date = calendrier.getDatecalendrier();

            if (date == null) {
                continue;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

            if ("WeekEnd1".equalsIgnoreCase(calendrier.getTypedate())) {
                weekend1 = day;
            } else if ("WeekEnd2".equalsIgnoreCase(calendrier.getTypedate())) {
                weekend2 = day;
            }
        }

        return new int[]{weekend1, weekend2};
    }

    public List<Date> getListDesJoursFeries() {

        return calendrierRepository
                .findByTypedateAndDatecalendrierGreaterThanEqual(
                        "FERIE",
                        new Date()
                )
                .stream()
                .map(Calendrier::getDatecalendrier)
                .filter(Objects::nonNull)
                .toList();
    }
}