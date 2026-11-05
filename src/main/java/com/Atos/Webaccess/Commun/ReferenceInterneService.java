package com.Atos.Webaccess.Commun;

import com.Atos.Webaccess.User.Entities.User;

import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReferenceInterneService {

    private final EntityManager entityManager;

    public String generateReference(
            String name,
            String typeOperation,
            User user
    ) {

        // Validate input
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (typeOperation == null || typeOperation.isBlank()) {
            throw new IllegalArgumentException("Operation type is required");
        }

        if (user == null || user.getBank() == null
                || user.getBank().getCode() == null
                || user.getBank().getCode().isBlank()) {
            throw new IllegalStateException(
                    "User bank information is missing"
            );
        }

        // 1. Normalize username (legacy behavior)
        String normalizedName = name.trim();

        if (normalizedName.length() >= 8) {
            normalizedName = normalizedName.substring(0, 8);
        } else {
            normalizedName = String.format("%8s", normalizedName);
        }

        // 2. Get establishment code from authenticated user's bank
        String establishment = user.getBank().getCode();

        // 3. Build reference prefix (legacy behavior)
        String prefix = normalizedName
                + typeOperation
                + "Mwa"
                + "00"
                + establishment
                + "00000"
                + "00";

        // 4. Get next SQL Server sequence value
        Number sequenceValue = (Number) entityManager
                .createNativeQuery(
                        "SELECT NEXT VALUE FOR dbo.GEN_ADDOPR"
                )
                .getSingleResult();

        // 5. Format sequence using 8 digits
        String sequence = String.format(
                "%08d",
                sequenceValue.longValue()
        );

        // 6. Generate date
        String date = LocalDate.now().format(
                DateTimeFormatter.BASIC_ISO_DATE
        );

        // 7. Return complete reference
        return prefix + date + sequence;
    }
}