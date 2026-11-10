
package com.Atos.Webaccess.Domain.ChargeBack;

import com.Atos.Webaccess.Commun.Param;
import com.Atos.Webaccess.Domain.ChargeBack.DTO.ChargeBackResponse;
import com.Atos.Webaccess.Domain.ChargeBack.DTO.ChargeBackSearchRequest;
import com.Atos.Webaccess.Domain.Operations.Operation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChargeBackServiceImpl implements ChargeBackService {

    @PersistenceContext
    private EntityManager entityManager;
    
    private final ChargeBackRepository chargeBackRepository;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final Set<String> DATE_FIELDS = Set.of(
            "datesaisie",
            "datepresentation",
            "datetraitement",
            "datereglement",
            "dateescompte"
    );

    private static final Map<String, String> AGENCY_FIELDS = new HashMap<>();

    static {
        AGENCY_FIELDS.put("agencebeneficiaire", "agencebeneficiaire");
        AGENCY_FIELDS.put("agenceremettante", "agenceremettante");
        AGENCY_FIELDS.put("agencetire", "agencetire");
    }

    @Override
    public List<ChargeBackResponse> search(ChargeBackSearchRequest request) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tuple> cq = cb.createTupleQuery();

        Root<ChargeBack> chargeBack = cq.from(ChargeBack.class);
        Root<Operation> operation = cq.from(Operation.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(
                chargeBack.get("idoperation"),
                operation.get("idoperation")
        ));

        // Legacy ChargeBack operation types
        List<Predicate> operationTypes = new ArrayList<>();

        if (Param.getCartepaiment() != null &&
                !Param.getCartepaiment().isBlank()) {
            operationTypes.add(cb.like(
                    operation.get("typeoperation"),
                    "_" + Param.getCartepaiment() + "_"
            ));
        }

        if (Param.getCarteretrai() != null &&
                !Param.getCarteretrai().isBlank()) {
            operationTypes.add(cb.like(
                    operation.get("typeoperation"),
                    "_" + Param.getCarteretrai() + "_"
            ));
        }

        if (!operationTypes.isEmpty()) {
            predicates.add(cb.or(
                    operationTypes.toArray(new Predicate[0])
            ));
        }

        // Specific operation type
        if (hasValue(request.getType_opr_carte()) &&
                !"-1".equals(request.getType_opr_carte())) {
            predicates.add(cb.equal(
                    operation.get("typeoperation"),
                    request.getType_opr_carte()
            ));
        }

        // Account
        if (hasValue(request.getCompte())) {
            predicates.add(cb.equal(
                    operation.get("numcomptetire"),
                    request.getCompte().trim()
            ));
        }

        // RIO
        if (hasValue(request.getRio())) {
            predicates.add(cb.like(
                    operation.get("rio"),
                    "%" + request.getRio().trim() + "%"
            ));
        }

        // IIO = REFERENCEINTERNE
        if (hasValue(request.getIio())) {
            predicates.add(cb.like(
                    operation.get("referenceinterne"),
                    "%" + request.getIio().trim() + "%"
            ));
        }

        // Origin
        if (hasValue(request.getOrigine()) &&
                !"-1".equals(request.getOrigine())) {

            if ("Aller".equalsIgnoreCase(request.getOrigine())) {
                predicates.add(cb.lessThan(
                        operation.get("origine"),
                        4
                ));
            } else {
                predicates.add(cb.greaterThanOrEqualTo(
                        operation.get("origine"),
                        4
                ));
            }
        }

        // Status
        if (hasValue(request.getStat_comp())) {

            switch (request.getStat_comp()) {
                case "1":
                    predicates.add(cb.equal(
                            operation.get("indicateurannulation"), "O"
                    ));
                    break;

                case "2":
                    predicates.add(cb.equal(
                            operation.get("indicateurrejet"), "O"
                    ));
                    break;

                case "3":
                    predicates.add(cb.equal(
                            operation.get("indicateurrepresentation"), "O"
                    ));
                    break;
            }
        }

        // Operation state
        if (hasValue(request.getEtat()) &&
                !"-1".equals(request.getEtat())) {
            predicates.add(cb.equal(
                    operation.get("etat"),
                    Integer.valueOf(request.getEtat())
            ));
        }

        // Drawer name
        if (hasValue(request.getNom_tire())) {
            predicates.add(cb.equal(
                    operation.get("nomtire"),
                    request.getNom_tire().trim()
            ));
        }

        // Beneficiary name
        if (hasValue(request.getNom_ben())) {
            predicates.add(cb.equal(
                    operation.get("nombeneficiaire"),
                    request.getNom_ben().trim()
            ));
        }

        // Rejection reason
        if (hasValue(request.getMotif_rej())) {
            predicates.add(cb.equal(
                    cb.substring(operation.get("motifrejet"), 3, 3),
                    request.getMotif_rej().substring(
                            0, Math.min(3, request.getMotif_rej().length())
                    )
            ));
        }

        // Minimum amount
        if (hasValue(request.getMtn_min())) {
            predicates.add(cb.greaterThanOrEqualTo(
                    operation.get("montantoperation"),
                    toLegacyAmount(request.getMtn_min())
            ));
        }

        // Maximum amount
        if (hasValue(request.getMtn_max())) {
            predicates.add(cb.lessThanOrEqualTo(
                    operation.get("montantoperation"),
                    toLegacyAmount(request.getMtn_max())
            ));
        }

        // Agency
        if (hasValue(request.getAgence()) &&
                !"-1".equals(request.getAgence()) &&
                !"Autres".equalsIgnoreCase(request.getAgence())) {

            String agency = request.getAgence().trim();
            String agencyField = AGENCY_FIELDS.get(request.getQuelagence());

            if (agencyField != null) {
                predicates.add(cb.equal(
                        operation.get(agencyField), agency
                ));
            } else {
                predicates.add(cb.or(
                        cb.equal(operation.get("agencebeneficiaire"), agency),
                        cb.equal(operation.get("agenceremettante"), agency),
                        cb.equal(operation.get("agencetire"), agency)
                ));
            }
        }

        // Date field: whitelist prevents arbitrary property selection
        String dateField = request.getTypeDate();

        if (dateField == null || !DATE_FIELDS.contains(dateField)) {
            dateField = "datesaisie";
        }

        if (hasValue(request.getDate_du())) {
            LocalDate start = LocalDate.parse(
                    request.getDate_du(), DATE_FORMAT
            );

            Date startDate = java.sql.Timestamp.valueOf(
                    start.atStartOfDay()
            );

            predicates.add(cb.greaterThanOrEqualTo(
                    operation.<Date>get(dateField),
                    startDate
            ));
        }

        if (hasValue(request.getDate_au())) {
            LocalDate end = LocalDate.parse(
                    request.getDate_au(), DATE_FORMAT
            );

            Date endExclusive = java.sql.Timestamp.valueOf(
                    end.plusDays(1).atStartOfDay()
            );

            predicates.add(cb.lessThan(
                    operation.<Date>get(dateField),
                    endExclusive
            ));
        }

        cq.select(cb.tuple(chargeBack, operation))
        .distinct(true)
        .where(predicates.toArray(new Predicate[0]))
        .orderBy(cb.asc(operation.get("ribtire")));
	
	List<Tuple> results = entityManager.createQuery(cq).getResultList();
	
	return results.stream()
	        .map(tuple -> new ChargeBackResponse(
	                tuple.get(1, Operation.class),
	                tuple.get(0, ChargeBack.class)
	        ))
	        .toList();
	}
    private boolean hasValue(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Legacy amounts are stored as 15-character cents strings
    private String toLegacyAmount(String amount) {

        BigDecimal cents = new BigDecimal(amount.trim())
                .movePointRight(2);

        String value = cents.toBigIntegerExact().toString();

        if (value.length() > 15) {
            throw new IllegalArgumentException(
                    "Amount exceeds the supported length"
            );
        }

        return "0".repeat(15 - value.length()) + value;
    }
    @Override
    public Optional<ChargeBack> findById(Integer id) {
        return chargeBackRepository.findById(id);
    }
}
