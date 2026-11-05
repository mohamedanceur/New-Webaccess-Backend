package com.Atos.Webaccess.Domain.effets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class EffetMapper {

    private EffetMapper() {
        // Utility class
    }

    public static Date parseDate(String dateValue) {
        if (dateValue == null || dateValue.isBlank()) {
            return null;
        }

        SimpleDateFormat formatter =
                new SimpleDateFormat("dd/MM/yyyy");

        formatter.setLenient(false);

        try {
            return formatter.parse(dateValue.trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "Invalid Effect date: " + dateValue, e);
        }
    }

    public static String normalizeAmount(String amount) {
        if (amount == null || amount.isBlank()) {
            return null;
        }

        String normalized = amount
                .replace(" ", "")
                .replace(",", ".")
                .replace(";", ".");

        int decimalIndex = normalized.indexOf('.');

        if (decimalIndex == -1) {
            return normalized;
        }

        if (normalized.indexOf('.', decimalIndex + 1) != -1) {
            throw new IllegalArgumentException(
                    "Invalid amount: " + amount);
        }

        String integerPart = normalized.substring(0, decimalIndex);
        String decimalPart = normalized.substring(decimalIndex + 1);

        if (decimalPart.length() < 2) {
            throw new IllegalArgumentException(
                    "Amount must contain two decimal digits: " + amount);
        }

        return integerPart + decimalPart.substring(0, 2);
    }
}