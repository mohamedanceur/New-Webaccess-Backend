
package com.Atos.Webaccess.Approve;

import com.Atos.Webaccess.Domain.Operations.Operation;
import com.Atos.Webaccess.Domain.Operations.DTO.OperationSearchRequest;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class ApproveOperationSpecifications {

    private ApproveOperationSpecifications() {
    }

    public static Specification<Operation> search(
            OperationSearchRequest request,
            SearchContext context) {

        if (request == null || context == null) {
            throw new IllegalArgumentException(
                    "APPROVE request and context are required");
        }

        validateRequest(request);

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Task state: empty means -1.
            String state = request.getEtatSelectTache();

            if (state == null || state.isBlank()) {
                state = "-1";
            }

            try {
                predicates.add(
                        cb.equal(
                                root.get("etat"),
                                Integer.valueOf(state.trim())
                        )
                );
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException(
                        "Invalid APPROVE task state: " + state, ex);
            }

            // Configured agency restriction.
            if (!"*".equals(context.agencyCode())) {

                String agencyField =
                        "A".equalsIgnoreCase(request.getSens())
                                ? "agencebeneficiaire"
                                : "agencetire";

                predicates.add(
                        cb.equal(
                                root.get(agencyField),
                                context.agencyCode()
                        )
                );
            }

            // Configured establishment restriction.
            if (!"*".equals(context.establishmentCode())) {
                predicates.add(
                        cb.equal(
                                root.get("etablissement"),
                                context.establishmentCode()
                        )
                );
            }

            if (query != null) {
                query.orderBy(cb.asc(root.get("ribtire")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void validateRequest(
            OperationSearchRequest request) {

        String sens = request.getSens();

        if (!"A".equalsIgnoreCase(sens)
                && !"R".equalsIgnoreCase(sens)) {
            throw new IllegalArgumentException(
                    "APPROVE direction must be A or R");
        }
    }

    public record SearchContext(
            String agencyCode,
            String establishmentCode,
            java.time.LocalDateTime startDate,
            java.time.LocalDateTime endDate
    ) {
        public SearchContext {
            if (agencyCode == null || establishmentCode == null) {
                throw new IllegalArgumentException(
                        "Agency and establishment configuration are required");
            }
        }
    }
}
