
package com.Atos.Webaccess.Domain.Operations.Specifications;

import com.Atos.Webaccess.Domain.Operations.Operation;
import com.Atos.Webaccess.Domain.Operations.DTO.OperationSearchRequest;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class OperationSpecifications {

    private OperationSpecifications() {
    }

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    private static final Map<String, String> DATE_FIELDS = Map.of(
            "datesaisie", "datesaisie",
            "datepresentation", "datepresentation",
            "datetraitement", "datetraitement",
            "datereglement", "datereglement",
            "dateescompte", "dateescompte"
    );

    private static final Set<String> AGENCY_FIELDS = Set.of(
            "agencebeneficiaire",
            "agenceremettante",
            "agencetire"
    );

    /**
     * Main search specification.
     *
     * SearchContext must be built on the backend using the authenticated
     * user's permissions and the application's bank/agency configuration.
     */
    public static Specification<Operation> search(
            OperationSearchRequest request,
            SearchContext context) {

        if (request == null) {
            throw new IllegalArgumentException(
                    "Operation search request cannot be null");
        }

        if (context == null) {
            throw new IllegalArgumentException(
                    "Operation search context cannot be null");
        }

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            /*
             * 1. Agency authorization and bank/agency selection
             */

            applyAgencyRestrictions(
                    request, context, root, cb, predicates);

            /*
             * 2. Amount range
             */

            if (hasText(request.getMtn_min())) {
                predicates.add(
                        cb.greaterThanOrEqualTo(
                                root.<String>get("montantoperation"),
                                toStoredAmount(request.getMtn_min())
                        )
                );
            }

            if (hasText(request.getMtn_max())) {
                predicates.add(
                        cb.lessThanOrEqualTo(
                                root.<String>get("montantoperation"),
                                toStoredAmount(request.getMtn_max())
                        )
                );
            }

            /*
             * 3. Account
             */

            if (hasText(request.getCompte())) {
                predicates.add(
                        cb.equal(
                                root.get("numcomptetire"),
                                request.getCompte().trim()
                        )
                );
            }

            /*
             * 4. RIO
             */

            if (hasText(request.getRio())) {
                predicates.add(
                        cb.like(
                                root.get("rio"),
                                "%" + escapeLike(request.getRio().trim()) + "%",
                                '\\'
                        )
                );
            }

            /*
             * 5. IIO / Internal reference
             */

            if (hasText(request.getIio())) {
                predicates.add(
                        cb.like(
                                root.get("referenceinterne"),
                                "%" + escapeLike(request.getIio().trim()) + "%",
                                '\\'
                        )
                );
            }

            /*
             * 6. Origin, status and state
             *
             * Bank 001 has special inter/intra origin rules in the legacy app.
             */

            if ("001".equalsIgnoreCase(context.currentBankCode())) {
                applyInterIntraOrigin(
                        request, context, root, cb, predicates);
            } else {
                applyStandardOrigin(
                        request, root, cb, predicates);
            }

            applyStatusAndState(request, root, cb, predicates);

            /*
             * 7. Drawer name
             */

            if (hasText(request.getNom_tire())) {
                predicates.add(
                        cb.equal(
                                root.get("nomtire"),
                                request.getNom_tire().trim()
                        )
                );
            }

            /*
             * 8. Beneficiary name
             */

            if (hasText(request.getNom_ben())) {
                predicates.add(
                        cb.equal(
                                root.get("nombeneficiaire"),
                                request.getNom_ben().trim()
                        )
                );
            }

            /*
             * 9. Rejection reason
             *
             * Legacy:
             * substring(OPERATIONS.motifrejet, 3, 5)
             * compared with the first 3 characters of the request.
             */

            if (hasText(request.getMotif_rej())) {

                String motif = request.getMotif_rej().trim();

                if (motif.length() < 3) {
                    throw new IllegalArgumentException(
                            "Rejection reason must contain at least 3 characters");
                }

                Expression<String> motifExpression =
                        cb.substring(root.get("motifrejet"), 3, 5);

                predicates.add(
                        cb.equal(
                                motifExpression,
                                motif.substring(0, 3)
                        )
                );
            }

            /*
             * 10. Operation-type restrictions from legacy gest_Priv()
             */

            applyOperationTypePermissions(
                    context, root, cb, predicates);

            /*
             * 11. Date range
             */

            applyDateRange(request, root, cb, predicates);

            /*
             * 12. Legacy ordering
             */

            if (query != null) {
                query.orderBy(cb.asc(root.get("ribtire")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /*
     * A convenience overload is intentionally NOT provided.
     *
     * Calling search without the authenticated user's SearchContext
     * would bypass the legacy agency and operation-type restrictions.
     */

    private static void applyAgencyRestrictions(
            OperationSearchRequest request,
            SearchContext context,
            jakarta.persistence.criteria.Root<Operation> root,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            List<Predicate> predicates) {

        List<String> authorizedAgencies =
                safeList(context.authorizedAgencies());

        List<String> allAgencies =
                safeList(context.allAgencies());

        String selectedAgency = valueOrDefault(
                request.getAgence(), "-1");

        String selectedAgencyField = valueOrDefault(
                request.getQuelagence(), "-1");

        /*
         * Legacy behavior:
         *
         * If agency = "Autres":
         * exclude all agencies belonging to the user's agency group.
         *
         * Otherwise:
         * restrict results to the user's authorized agency group.
         */

        if ("Autres".equalsIgnoreCase(selectedAgency)) {

            if (!context.searchOtherAgencies()) {
                throw new org.springframework.security.access.AccessDeniedException(
                        "You are not authorized to search other agencies."
                );
            }

            if (allAgencies.isEmpty()) {
                predicates.add(cb.disjunction());
                return;
            }

            for (String agency : allAgencies) {
                predicates.add(
                        cb.and(
                                cb.notEqual(
                                        root.get("agencebeneficiaire"), agency),
                                cb.notEqual(
                                        root.get("agenceremettante"), agency),
                                cb.notEqual(
                                        root.get("agencetire"), agency)
                        )
                );
            }

            return;
        }

        /*
         * Agency-group restriction.
         * The legacy query requires at least one agency match.
         */

        if (authorizedAgencies.isEmpty()) {
            predicates.add(cb.disjunction());
            return;
        }

        List<Predicate> groupPredicates = new ArrayList<>();

        for (String agency : authorizedAgencies) {
            if (!hasText(agency)) {
                continue;
            }

            groupPredicates.add(
                    cb.or(
                            cb.equal(
                                    root.get("agencebeneficiaire"), agency),
                            cb.equal(
                                    root.get("agenceremettante"), agency),
                            cb.equal(
                                    root.get("agencetire"), agency)
                    )
            );
        }

        if (groupPredicates.isEmpty()) {
            predicates.add(cb.disjunction());
            return;
        }

        predicates.add(
                cb.or(groupPredicates.toArray(new Predicate[0]))
        );

        /*
         * Bank/agency selection.
         */

        if ("-1".equals(selectedAgency)) {

            String bankPrefix = resolveBankPrefix(
                    request.getBanque(), context);

            if (!hasText(bankPrefix)) {
                throw new IllegalArgumentException(
                        "Unable to resolve the selected bank");
            }

            String pattern = escapeLike(bankPrefix) + "%";

            if ("-1".equals(selectedAgencyField)) {

                predicates.add(
                        cb.or(
                                cb.like(
                                        root.get("agencebeneficiaire"),
                                        pattern,
                                        '\\'
                                ),
                                cb.like(
                                        root.get("agenceremettante"),
                                        pattern,
                                        '\\'
                                ),
                                cb.like(
                                        root.get("agencetire"),
                                        pattern,
                                        '\\'
                                )
                        )
                );

            } else {

                validateAgencyField(selectedAgencyField);

                predicates.add(
                        cb.like(
                                root.get(selectedAgencyField),
                                pattern,
                                '\\'
                        )
                );
            }

        } else {

            if ("-1".equals(selectedAgencyField)) {

                predicates.add(
                        cb.or(
                                cb.equal(
                                        root.get("agencebeneficiaire"),
                                        selectedAgency
                                ),
                                cb.equal(
                                        root.get("agenceremettante"),
                                        selectedAgency
                                ),
                                cb.equal(
                                        root.get("agencetire"),
                                        selectedAgency
                                )
                        )
                );

            } else {

                validateAgencyField(selectedAgencyField);

                predicates.add(
                        cb.equal(
                                root.get(selectedAgencyField),
                                selectedAgency
                        )
                );
            }
        }
    }

    private static void applyStandardOrigin(
            OperationSearchRequest request,
            jakarta.persistence.criteria.Root<Operation> root,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            List<Predicate> predicates) {

        String origine = valueOrDefault(
                request.getOrigine(), "-1");

        switch (origine.toLowerCase(java.util.Locale.ROOT)) {

            case "-1" -> {
                // No origin filter.
            }

            case "aller" -> predicates.add(
                    cb.lessThan(root.get("origine"), 4)
            );

            case "retour" -> predicates.add(
                    cb.greaterThan(root.get("origine"), 3)
            );

            default -> throw new IllegalArgumentException(
                    "Invalid operation origin: " + origine
            );
        }
    }

    private static void applyInterIntraOrigin(
            OperationSearchRequest request,
            SearchContext context,
            jakarta.persistence.criteria.Root<Operation> root,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            List<Predicate> predicates) {

        if (!hasText(request.getOrigine())
                || "-1".equals(request.getOrigine())) {
            return;
        }

        List<String> authorizedAgencies =
                safeList(context.authorizedAgencies());

        if (authorizedAgencies.isEmpty()) {
            predicates.add(cb.disjunction());
            return;
        }

        boolean isAller =
                "Aller".equalsIgnoreCase(request.getOrigine());

        if (isAller) {

            /*
             * Legacy:
             *
             * (origine < 4 AND banquetire != banquebeneficiaire)
             * OR
             * (origine < 4 AND banquetire = banquebeneficiaire
             *  AND agencebeneficiaire IN authorized agencies)
             */

            predicates.add(
                    cb.and(
                            cb.lessThan(root.get("origine"), 4),
                            cb.or(
                                    cb.notEqual(
                                            root.get("banquetire"),
                                            root.get("banquebeneficiaire")
                                    ),
                                    cb.and(
                                            cb.equal(
                                                    root.get("banquetire"),
                                                    root.get("banquebeneficiaire")
                                            ),
                                            root.get("agencebeneficiaire")
                                                    .in(authorizedAgencies)
                                    )
                            )
                    )
            );

        } else {

            /*
             * Legacy:
             *
             * origine > 3
             * OR
             * (origine < 4 AND agencetire IN authorized agencies
             *  AND banquetire = banquebeneficiaire)
             */

            predicates.add(
                    cb.or(
                            cb.greaterThan(root.get("origine"), 3),
                            cb.and(
                                    cb.lessThan(root.get("origine"), 4),
                                    root.get("agencetire")
                                            .in(authorizedAgencies),
                                    cb.equal(
                                            root.get("banquetire"),
                                            root.get("banquebeneficiaire")
                                    )
                            )
                    )
            );
        }
    }

    private static void applyStatusAndState(
            OperationSearchRequest request,
            jakarta.persistence.criteria.Root<Operation> root,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            List<Predicate> predicates) {

        String status = valueOrDefault(
                request.getStat_comp(), "-1");

        switch (status) {
            case "1" ->
                    predicates.add(
                            cb.equal(root.get("indicateurannulation"), "O")
                    );

            case "2" ->
                    predicates.add(
                            cb.equal(root.get("indicateurrejet"), "O")
                    );

            case "3" ->
                    predicates.add(
                            cb.equal(root.get("indicateurrepresentation"), "O")
                    );

            case "-1" -> {
            }

            default -> throw new IllegalArgumentException(
                    "Invalid operation status: " + status);
        }

        String state = valueOrDefault(request.getEtat(), "-1");

        if (!"-1".equals(state)) {
            try {
                predicates.add(
                        cb.equal(
                                root.get("etat"),
                                Integer.valueOf(state)
                        )
                );
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Invalid operation state: " + state);
            }
        }
    }

    private static void applyOperationTypePermissions(
            SearchContext context,
            jakarta.persistence.criteria.Root<Operation> root,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            List<Predicate> predicates) {

        List<Predicate> allowedTypes = new ArrayList<>();

        /*
         * These are the same permission conditions as legacy gest_Priv().
         * The operation codes are supplied by the backend configuration.
         */

        if (context.searchCards()) {
            addOperationTypePattern(
                    allowedTypes, cb, root, context.cardPaymentCode());

            addOperationTypePattern(
                    allowedTypes, cb, root, context.cardWithdrawalCode());
        }

        if (context.searchCheques()) {
            addOperationTypePattern(
                    allowedTypes, cb, root, context.chequeCode());
        }

        if (context.searchEffects()) {
            addOperationTypePattern(
                    allowedTypes, cb, root, context.effectCode());
        }

        if (context.searchDirectDebits()) {
            addOperationTypePattern(
                    allowedTypes, cb, root, context.directDebitCode());
        }

        if (context.searchTransfers()) {
            addOperationTypePattern(
                    allowedTypes, cb, root, context.transferCode());
        }

        if (context.searchPMobile()) {
            addOperationTypePattern(
                    allowedTypes, cb, root, context.pMobileCode());
        }

        /*
         * Legacy gest_Priv() ends with "1<>1".
         * If no search permission is granted, no operation is visible.
         */

        if (allowedTypes.isEmpty()) {
            predicates.add(cb.disjunction());
        } else {
            predicates.add(
                    cb.or(allowedTypes.toArray(new Predicate[0]))
            );
        }
    }

    private static void addOperationTypePattern(
            List<Predicate> predicates,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            jakarta.persistence.criteria.Root<Operation> root,
            String operationCode) {

        if (!hasText(operationCode)) {
            return;
        }

        /*
         * Same pattern as the legacy SQL:
         * OPERATIONS.typeoperation LIKE '_<code>_'
         */
        predicates.add(
                cb.like(
                        root.get("typeoperation"),
                        "_" + operationCode.trim() + "_"
                )
        );
    }

    private static void applyDateRange(
            OperationSearchRequest request,
            jakarta.persistence.criteria.Root<Operation> root,
            jakarta.persistence.criteria.CriteriaBuilder cb,
            List<Predicate> predicates) {

        String requestedDateField = valueOrDefault(
                request.getTypeDate(), "datesaisie")
                .toLowerCase();

        String dateField = DATE_FIELDS.get(requestedDateField);

        if (dateField == null) {
            throw new IllegalArgumentException(
                    "Invalid operation date field: " + requestedDateField);
        }

        if (hasText(request.getDate_du())) {

            LocalDate startDate = parseDate(request.getDate_du());

            Timestamp start = Timestamp.valueOf(
                    startDate.atStartOfDay()
            );

            predicates.add(
                    cb.greaterThanOrEqualTo(
                            root.<Date>get(dateField),
                            start
                    )
            );
        }

        if (hasText(request.getDate_au())) {

            LocalDate endDate = parseDate(request.getDate_au());

            /*
             * Exclusive upper bound avoids missing records with
             * fractional seconds on the final day.
             */
            Timestamp endExclusive = Timestamp.valueOf(
                    endDate.plusDays(1).atStartOfDay()
            );

            predicates.add(
                    cb.lessThan(
                            root.<Date>get(dateField),
                            endExclusive
                    )
            );
        }
    }

    private static LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(
                    value.trim(),
                    DATE_FORMATTER
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid date. Expected format: dd/MM/yyyy. Value: "
                            + value);
        }
    }

    private static String resolveBankPrefix(
            String selectedBank,
            SearchContext context) {

        if (!hasText(selectedBank)
                || "-1".equals(selectedBank.trim())) {
            throw new IllegalArgumentException(
                    "A bank must be selected when searching all agencies");
        }

        try {
            Integer bankId = Integer.valueOf(selectedBank.trim());

            return context.bankCodes().get(bankId);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid bank identifier: " + selectedBank);
        }
    }

    private static void validateAgencyField(String field) {
        if (!AGENCY_FIELDS.contains(field)) {
            throw new IllegalArgumentException(
                    "Invalid agency field: " + field);
        }
    }

    /**
     * Legacy amount format:
     * 15-character string representing cents.
     *
     * Example:
     * 125.50 -> 000000000012550
     */
    private static String toStoredAmount(String value) {

        final BigDecimal amount;

        try {
            amount = new BigDecimal(
                    value.trim().replace(" ", "").replace(",", ".")
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid amount: " + value);
        }

        if (amount.signum() < 0) {
            throw new IllegalArgumentException(
                    "Amount cannot be negative");
        }

        String cents;

        try {
            cents = amount
                    .movePointRight(2)
                    .setScale(0, RoundingMode.UNNECESSARY)
                    .toPlainString();
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(
                    "Amount cannot contain more than two decimal places");
        }

        if (cents.length() > 15) {
            throw new IllegalArgumentException(
                    "Amount exceeds the supported length");
        }

        return "0".repeat(15 - cents.length()) + cents;
    }

    private static List<String> safeList(List<String> values) {
        if (values == null) {
            return List.of();
        }

        return values.stream()
                .filter(OperationSpecifications::hasText)
                .map(String::trim)
                .distinct()
                .toList();
    }

    private static String valueOrDefault(
            String value,
            String defaultValue) {

        return hasText(value) ? value.trim() : defaultValue;
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static String escapeLike(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }

    /**
     * Backend-only security/configuration context.
     *
     * Build this from the authenticated user, Priv, Param and
     * agency/bank configuration. Never accept it from the frontend.
     */
    public record SearchContext(

            String currentBankCode,

            List<String> authorizedAgencies,
            List<String> allAgencies,

            Map<Integer, String> bankCodes,

            boolean searchCards,
            boolean searchCheques,
            boolean searchEffects,
            boolean searchDirectDebits,
            boolean searchTransfers,
            boolean searchPMobile,

            String cardPaymentCode,
            String cardWithdrawalCode,
            String chequeCode,
            String effectCode,
            String directDebitCode,
            String transferCode,
            String pMobileCode,

            boolean searchOtherAgencies
            
            

    ) {
    }
}
