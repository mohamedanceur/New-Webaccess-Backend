package com.Atos.Webaccess.Approve.service;

import com.Atos.Webaccess.Domain.Banque.Banque;
import com.Atos.Webaccess.Domain.Libelle.Libelle;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class CommunService {

    public Map<Integer, Libelle> mapLibelle(List<Libelle> labels) {

        Map<Integer, Libelle> result = new HashMap<>();

        for (Libelle label : labels) {
            if (label.getCode() != null) {
                result.put(Integer.parseInt(label.getCode()), label);
            }
        }

        return result;
    }

    public Map<String, Libelle> mapLibelleTypev(List<Libelle> labels) {

        Map<String, Libelle> result = new HashMap<>();

        for (Libelle label : labels) {
            result.put(label.getLibelle1(), label);
        }

        return result;
    }

    public Map<String, Libelle> mapLibelleVar(List<Libelle> labels) {

        Map<String, Libelle> result = new HashMap<>();

        for (Libelle label : labels) {
            result.put(label.getCode(), label);
        }

        return result;
    }

    public Map<String, Libelle> mapLibelleCodeString(
            List<Libelle> labels) {

        Map<String, Libelle> result = new HashMap<>();

        for (Libelle label : labels) {
            result.put(label.getCode(), label);
        }

        return result;
    }

    public Map<Integer, String> mapCodeBanque(List<Banque> banks) {

        Map<Integer, String> result = new HashMap<>();

        for (Banque bank : banks) {
            result.put(bank.getId(), bank.getCode());
        }

        return result;
    }

    public Map<String, Integer> mapIdCodeBanque(List<Banque> banks) {

        Map<String, Integer> result = new HashMap<>();

        for (Banque bank : banks) {
            result.put(bank.getCode(), bank.getId());
        }

        return result;
    }

    public String getMotifRejet(
            Map<String, Libelle> rejectionReasons,
            String rejectionCode) {

        if (rejectionCode == null || rejectionCode.length() < 5) {
            return null;
        }

        String lookupCode;

        if (rejectionCode.startsWith("SC")
                || rejectionCode.startsWith("UA")
                || rejectionCode.startsWith("CTL")) {
            lookupCode = rejectionCode;
        } else {
            lookupCode = rejectionCode.substring(2, 5);
        }

        Libelle label = rejectionReasons.get(lookupCode);

        return label == null
                ? rejectionCode
                : label.getLibelle2();
    }

    public String constructNomUser(String username) {

        if (username == null) {
            return "        ";
        }

        String value = username.trim();

        if (value.length() >= 8) {
            return value.substring(0, 8);
        }

        return String.format("%8s", value);
    }

    public boolean validateHeure(String time) {

        if (time == null) {
            return false;
        }

        try {
            LocalTime.parse(
                    time,
                    DateTimeFormatter.ofPattern("hh:mm:ss")
            );
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    public int compareHeurePosition(String time) {

        if (time == null) {
            throw new IllegalArgumentException("Time must not be null");
        }

        LocalTime currentTime = LocalTime.now();

        LocalTime targetTime = LocalTime.parse(
                time,
                DateTimeFormatter.ofPattern("HH:mm:ss")
        );

        return currentTime.isAfter(targetTime) ? 1 : 0;
    }

    public String addDaysToCalender(int numberOfDays) {

        return LocalDate.now()
                .plusDays(numberOfDays)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String openPopUp(String page, String title) {

        // Keep this only as a compatibility helper.
        // Prefer building popup behavior in the frontend.
        return "window.open('" + page + "', '" + title
                + "', 'top=25,left=20,height=600,width=1000,"
                + "toolbar=no,menubar=no,scrollbars=yes,resizable=yes')";
    }
}