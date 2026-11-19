
package com.Atos.Webaccess.Approve;

import com.Atos.Webaccess.Domain.Operations.Operation;
import com.Atos.Webaccess.Domain.Operations.DTO.ApproveSearchRequest;
import com.Atos.Webaccess.Domain.Operations.OperationTypeCodeProperties;
import com.Atos.Webaccess.Domain.cheques.Cheque;
import com.Atos.Webaccess.Domain.effets.Effet;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class ApproveInstrumentSpecifications {

    private ApproveInstrumentSpecifications() {
    }

    public static Specification<Operation> cheque(
            ApproveSearchRequest request,
            ApproveOperationSpecifications.SearchContext context,
            OperationTypeCodeProperties codes) {

        if (codes == null || codes.getCheque() == null
                || codes.getCheque().isBlank()) {
            throw new IllegalStateException(
                    "Cheque operation code is not configured");
        }

        return build(request, context, codes.getCheque(), true);
    }

    public static Specification<Operation> effet(
            ApproveSearchRequest request,
            ApproveOperationSpecifications.SearchContext context) {

        return build(request, context, null, false);
    }

    private static Specification<Operation> build(
            ApproveSearchRequest request,
            ApproveOperationSpecifications.SearchContext context,
            String chequeCode,
            boolean cheque) {

        if (request == null || context == null) {
            throw new IllegalArgumentException(
                    "APPROVE request and context are required");
        }

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Instrument relation:
            // OPERATIONS.IDINSTRUMENT = CHEQUES.IDCHEQUE
            // OPERATIONS.IDINSTRUMENT = EFFETS.IDEFFET
            if (cheque) {

                Subquery<Integer> sq = query.subquery(Integer.class);
                var instrument = sq.from(Cheque.class);

                sq.select(instrument.get("idcheque"));
                sq.where(cb.equal(
                        instrument.get("idcheque"),
                        root.get("idinstrument")
                ));

                predicates.add(cb.exists(sq));

                // Legacy cheque operation type: prefix 0.
                predicates.add(cb.like(
                        root.get("typeoperation"),
                        "0" + chequeCode + "%"
                ));

            } else {

                Subquery<Integer> sq = query.subquery(Integer.class);
                var instrument = sq.from(Effet.class);

                sq.select(instrument.get("ideffet"));
                sq.where(cb.equal(
                        instrument.get("ideffet"),
                        root.get("idinstrument")
                ));

                predicates.add(cb.exists(sq));
            }

            // Legacy special case: task 105 always uses origin < 4.
            boolean task105 =
                    "105".equals(request.getEtatSelectTache());

            if ("A".equalsIgnoreCase(request.getSens()) || task105) {
                predicates.add(cb.lessThan(root.get("origine"), 4));
            } else {
                predicates.add(cb.greaterThan(root.get("origine"), 3));
            }

            // Legacy beneficiary bank filter exists for Effet.
            if (!cheque) {
                String bank = request.getApproveBeneficiaryBank();

                if (bank != null && !bank.isBlank()
                        && !"-1".equals(bank)) {
                    predicates.add(cb.equal(
                            root.get("banquebeneficiaire"), bank));
                }
            }

            // Legacy date behavior:
            // Cheque: startDate <= DATEREGLEMENT <= endDate
            // Effet:  DATEREGLEMENT > today at 23:59:59
            if (cheque) {

                if (context.startDate() == null
                        || context.endDate() == null) {
                    throw new IllegalArgumentException(
                            "Cheque search date range is required");
                }

                Date start = toDate(context.startDate());
                Date end = toDate(context.endDate());

                predicates.add(cb.greaterThanOrEqualTo(
                        root.<Date>get("datereglement"), start));

                predicates.add(cb.lessThanOrEqualTo(
                        root.<Date>get("datereglement"), end));

            } else {

                Date todayEnd = toDate(
                        LocalDateTime.now()
                                .withHour(23)
                                .withMinute(59)
                                .withSecond(59)
                                .withNano(0)
                );

                predicates.add(cb.greaterThan(
                        root.<Date>get("datereglement"), todayEnd));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Date toDate(LocalDateTime value) {
        return Date.from(
                value.atZone(ZoneId.systemDefault()).toInstant());
    }
}
