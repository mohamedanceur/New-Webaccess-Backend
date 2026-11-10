package com.Atos.Webaccess.Domain.Prelevement;


import com.Atos.Webaccess.Domain.Prelevement.DTO.PrelevementRequest;
import com.Atos.Webaccess.Domain.Prelevement.DTO.PrelevementResponse;
import com.Atos.Webaccess.Domain.Prelevement.opPrelevementEnAttente.OpPrelevementEnAttente;
import com.Atos.Webaccess.Domain.Prelevement.opPrelevementEnAttente.OpPrelevementEnAttenteRepository;
import com.Atos.Webaccess.Domain.Evenement.EvenementService;
import com.Atos.Webaccess.Domain.Operations.OperationRepository;
import com.Atos.Webaccess.Domain.Operations.Operation;
import com.Atos.Webaccess.Domain.Evenement.Evenement;
import com.Atos.Webaccess.Commun.Param;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PrelevementServiceImpl implements PrelevementService {

	private final PrelevementRepository prelevementRepository;
    private final OpPrelevementEnAttenteRepository pendingRepository;

    private final OperationRepository operationRepository;
    private final EvenementService evenementService;
    


    @Override
    public void deletePrelevement(Integer idPrelevement, Integer idOperation) {

        Prelevement prelevement = prelevementRepository.findById(idPrelevement)
                .orElseThrow(() ->
                        new EntityNotFoundException("Prelevement not found: " + idPrelevement));

        OpPrelevementEnAttente pending = pendingRepository.findById(idOperation)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pending operation not found: " + idOperation));

        prelevementRepository.delete(prelevement);
        pendingRepository.delete(pending);
    }

    @Override
    public void addPrelevement(
            PrelevementRequest request,
            String typeOperation,
            String devise,
            String reference,
            String username,
            String host,
            String motifRejet) {

        Objects.requireNonNull(request, "Prelevement request is required");

        Prelevement prelevement = new Prelevement();

        prelevement.setDateexecution(formatDateForDatabase(request.getDateexecution()));
        prelevement.setDatefacture(formatDateForDatabase(request.getDatefacture()));
        prelevement.setDonneesemetteur(request.getDonneesemetteur());
        prelevement.setEtablissement(Param.getLeEtablissement());
        prelevement.setLibelle(request.getLibelle());
        prelevement.setNumeroemetteur(request.getNumeroemetteur());

        OpPrelevementEnAttente pending = new OpPrelevementEnAttente();

        pending.setNumcomptebeneficiaire(request.getNumcomptebeneficiaire());
        pending.setAgenceremettante(request.getAgencebeneficiaire());
        pending.setDatetraitement(new Date());
        pending.setDevise(devise);
        pending.setNumcomptetire(request.getNumcomptetire());

        pending.setBanquetire(
                substringSafe(request.getAgencetire(), 0, 3));

        pending.setAgencebeneficiaire(request.getAgencebeneficiaire());
        pending.setEtablissement(Param.getLeEtablissement());
        pending.setAgencetire(request.getAgencetire());
        pending.setAdressetire(request.getAdressetire());
        pending.setLibelleoperation(request.getLibelle());

        pending.setRibbeneficiaire(
                nullToEmpty(request.getAgencebeneficiaire())
                + nullToEmpty(request.getNumcomptebeneficiaire())
                + nullToEmpty(request.getClebeneficiaire()));

        pending.setDatesaisie(parseDate(request.getDateremise()));

        pending.setMontantoperation(toCents(request.getMontant()));

        pending.setRibtire(
                nullToEmpty(request.getAgencetire())
                + nullToEmpty(request.getNumcomptetire())
                + nullToEmpty(request.getCletire()));

        pending.setNombeneficiaire(request.getNombeneficiaire());
        pending.setEtat(Integer.parseInt(Param.getParamPrelevementAValider()));

        pending.setBanquebeneficiaire(
                substringSafe(request.getAgencebeneficiaire(), 0, 3));

        pending.setMotifrejet(motifRejet);
        pending.setAdressebeneficiaire(request.getAdressebeneficiaire());
        pending.setOrigine(1);
        pending.setTypeoperation(typeOperation);
        pending.setNomtire(request.getNomtire());

        // Save the instrument first.
        prelevementRepository.save(prelevement);

        // Save pending operation to obtain its generated SQL Server ID.
        pending.setIdinstrument(prelevement.getIdprelevement());
        pendingRepository.save(pending);

        // Generate the reference using the generated ID.
        String generatedReference = reference
                + new SimpleDateFormat("yyyyMMdd").format(new Date())
                + String.format("%08d", pending.getIdoperation());

        pending.setReferenceinterne(generatedReference);
        pendingRepository.save(pending);

        // Link PRELEVEMENTS to the pending operation.
        prelevement.setIdoperation(pending.getIdoperation());
        prelevementRepository.save(prelevement);

        savePrelevementEvent(
                pending.getIdoperation(),
                pending.getEtablissement(),
                username,
                host,
                "0",
                Param.getParamPrelevementAValider()
        );
    }

    @Override
    public void updatePrelevement(
            PrelevementRequest request,
            String typeOperation,
            String devise,
            String reference,
            String username,
            String host,
            String motifRejet) {

        Prelevement prelevement = prelevementRepository.findById(
                request.getIdprelevement()
        ).orElseThrow(() -> new EntityNotFoundException(
                "Prelevement not found: " + request.getIdprelevement()));

        OpPrelevementEnAttente pending = pendingRepository.findById(
                request.getIdoperation()
        ).orElseThrow(() -> new EntityNotFoundException(
                "Pending operation not found: " + request.getIdoperation()));

        prelevement.setDateexecution(formatDateForDatabase(request.getDateexecution()));
        prelevement.setDonneesemetteur(request.getDonneesemetteur());
        prelevement.setEtablissement(Param.getLeEtablissement());
        prelevement.setLibelle(request.getLibelle());
        prelevement.setDatefacture(formatDateForDatabase(request.getDatefacture()));
        prelevement.setNumeroemetteur(request.getNumeroemetteur());

        pending.setNumcomptebeneficiaire(request.getNumcomptebeneficiaire());
        pending.setAgenceremettante(request.getAgencebeneficiaire());
        pending.setDatetraitement(new Date());
        pending.setDevise(devise);
        pending.setNumcomptetire(request.getNumcomptetire());
        pending.setBanquetire(request.getBanquetire());
        pending.setAgencebeneficiaire(request.getAgencebeneficiaire());
        pending.setEtablissement(Param.getLeEtablissement());
        pending.setAgencetire(request.getAgencetire());
        pending.setAdressetire(request.getAdressetire());
        pending.setLibelleoperation(request.getLibelle());

        pending.setRibbeneficiaire(
                nullToEmpty(request.getAgencebeneficiaire())
                + nullToEmpty(request.getNumcomptebeneficiaire())
                + nullToEmpty(request.getClebeneficiaire()));

        pending.setDatesaisie(parseDate(request.getDateremise()));
        pending.setReferenceinterne(reference);
        pending.setMontantoperation(toCents(request.getMontant()));

        // Preserving the legacy mapping exactly.
        pending.setRibtire(
                nullToEmpty(request.getAdressetire())
                + nullToEmpty(request.getNumcomptetire())
                + nullToEmpty(request.getCletire()));

        pending.setNombeneficiaire(request.getNombeneficiaire());
        pending.setEtat(Integer.parseInt(Param.getParamPrelevementAValider()));
        pending.setBanquebeneficiaire(request.getBanquebeneficiaire());
        pending.setMotifrejet(motifRejet);
        pending.setAdressebeneficiaire(request.getAdressebeneficiaire());
        pending.setOrigine(1);
        pending.setTypeoperation(typeOperation);
        pending.setNomtire(request.getNomtire());

        prelevementRepository.save(prelevement);
        pendingRepository.save(pending);
    }

    @Override
    public void validatePrelevements(
            List<Integer> operationIds,
            String username,
            String host) {

        for (Integer id : operationIds) {
            try {
                validateOnePrelevement(id, username, host);
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Error validating prelevement: " + id, e);
            }
        }
    }

    private void validateOnePrelevement(
            Integer idOperation,
            String username,
            String host) throws Exception  {

        OpPrelevementEnAttente pending = pendingRepository.findById(idOperation)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Pending prelevement not found: " + idOperation));

        String reference = pending.getReferenceinterne();

        // Check whether this reference already exists
        if (operationRepository.findByReferenceinterne(reference).isEmpty()) {

            Operation operation = new Operation();

            operation.setIdinstrument(pending.getIdinstrument());
            operation.setNumcomptebeneficiaire(pending.getNumcomptebeneficiaire());
            operation.setIdlotinti(pending.getIdlotinti());
            operation.setIdoperationrejet(pending.getIdoperationrejet());
            operation.setAgenceremettante(pending.getAgenceremettante());
            operation.setIdlotinto(pending.getIdlotinto());
            operation.setDatetraitement(pending.getDatetraitement());
            operation.setDevise(pending.getDevise());
            operation.setNumcomptetire(pending.getNumcomptetire());
            operation.setBanquetire(pending.getBanquetire());
            operation.setCodeplace(pending.getCodeplace());
            operation.setAgencebeneficiaire(pending.getAgencebeneficiaire());
            operation.setEtablissement(pending.getEtablissement());
            operation.setAgencetire(pending.getAgencetire());
            operation.setAdressetire(pending.getAdressetire());
            operation.setLibelleoperation(pending.getLibelleoperation());
            operation.setRibbeneficiaire(pending.getRibbeneficiaire());
            operation.setIbanbeneficiaire(pending.getIbanbeneficiaire());
            operation.setDatesaisie(pending.getDatesaisie());
            operation.setReferenceinterne(reference);
            operation.setDatepresentation(pending.getDatepresentation());
            operation.setMontantoperation(
            	    pending.getMontantoperation() != null
            	        ? pending.getMontantoperation().toPlainString()
            	        : null
            	);
            operation.setRibtire(pending.getRibtire());
            operation.setNombeneficiaire(pending.getNombeneficiaire());
            operation.setEtat(Integer.valueOf(Param.getParamPrelevementValide()));
            operation.setBanquebeneficiaire(pending.getBanquebeneficiaire());
            operation.setMotifrejet(pending.getMotifrejet());
            operation.setIdlotuap(pending.getIdlotuap());
            operation.setIdimageuap(pending.getIdimageuap());
            operation.setAdressebeneficiaire(pending.getAdressebeneficiaire());
            operation.setOrigine(pending.getOrigine());
            operation.setIbantire(pending.getIbantire());
            operation.setIndicateurannulation("N");
            operation.setIndicateurrejet("N");
            operation.setIndicateurrepresentation("N");
            operation.setRio(pending.getRio());
            operation.setTypeoperation(pending.getTypeoperation());
            operation.setIdfichierimgin(pending.getIdfichierimgin());
            operation.setDatereglement(pending.getDatereglement());
            operation.setIdremise(pending.getIdremise());
            operation.setNomtire(pending.getNomtire());
            operation.setIdfichierimgout(pending.getIdfichierimgout());
            operation.setIdfichiercra(pending.getIdfichiercra());

            operationRepository.save(operation);

            // Update the related PRELEVEMENTS record
            prelevementRepository.findById(pending.getIdinstrument())
                    .ifPresent(prelevement -> {
                        prelevement.setIdoperation(operation.getIdoperation());
                        prelevementRepository.save(prelevement);
                    });

            // Record the validation event
            Evenement evenement = evenementService.addEvenement_CRE(
                    operation.getIdoperation(),
                    pending.getEtablissement(),
                    username,
                    host,
                    Param.getParamPrelevementAValider(),
                    Param.getParamPrelevementValide()
            );

            evenementService.save(evenement);
        }

        // Legacy behavior: remove the pending record even if the reference exists.
        pendingRepository.delete(pending);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrelevementResponse> getPendingPrelevements(
            Integer etat,
            Integer origine,
            String typeOperation,
            Integer idGroupe) {

        List<OpPrelevementEnAttente> operations = pendingRepository.findAll();

        List<PrelevementResponse> result = new ArrayList<>();

        for (OpPrelevementEnAttente pending : operations) {

            if (pending.getTypeoperation() == null
                    || pending.getTypeoperation().length() != 3
                    || pending.getTypeoperation().charAt(1) != '2') {
                continue;
            }

            Prelevement prelevement = prelevementRepository
                    .findById(pending.getIdinstrument())
                    .orElse(null);

            if (prelevement == null) {
                continue;
            }

            PrelevementResponse response = new PrelevementResponse();

            response.setIdoperation(pending.getIdoperation());
            response.setIdprelevement(prelevement.getIdprelevement());
            response.setIdinstrument(pending.getIdinstrument());

            response.setDonneesemetteur(prelevement.getDonneesemetteur());
            response.setNumeroemetteur(prelevement.getNumeroemetteur());

            response.setBanquetire(pending.getBanquetire());
            response.setMontantoperation(toDecimal(pending.getMontantoperation()));

            response.setOrigine(pending.getOrigine());
            response.setRio(pending.getRio());
            response.setReferenceinterne(pending.getReferenceinterne());

            response.setTypeoperation(pending.getTypeoperation());
            response.setEtat(pending.getEtat());

            result.add(response);
        }

        result.sort((a, b) -> {
            String first = Optional.ofNullable(a.getDonneesemetteur()).orElse("");
            String second = Optional.ofNullable(b.getDonneesemetteur()).orElse("");
            return first.compareToIgnoreCase(second);
        });

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PrelevementResponse getPrelevementDetails(Integer idOperation) {

        Operation operation = operationRepository.findById(idOperation)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Operation not found: " + idOperation));

        Prelevement prelevement = prelevementRepository
                .findById(operation.getIdinstrument())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Prelevement not found for operation: " + idOperation));

        PrelevementResponse response = new PrelevementResponse();

        response.setEtablissement(operation.getEtablissement());
        response.setIdoperation(operation.getIdoperation());

        response.setIdlotuap(operation.getIdlotuap());
        response.setIdlotinti(operation.getIdlotinti());
        response.setIdlotinto(operation.getIdlotinto());

        response.setIdfichierimgin(operation.getIdfichierimgin());
        response.setIdfichierimgout(operation.getIdfichierimgout());
        response.setIdremise(operation.getIdremise());

        response.setTypeoperation(operation.getTypeoperation());
        response.setIdinstrument(operation.getIdinstrument());
        response.setReferenceinterne(operation.getReferenceinterne());
        response.setEtat(operation.getEtat());
        response.setOrigine(operation.getOrigine());

        response.setRibtire(operation.getRibtire());
        response.setIbantire(operation.getIbantire());
        response.setBanquetire(operation.getBanquetire());
        response.setAgencetire(operation.getAgencetire());
        response.setNumcomptetire(operation.getNumcomptetire());
        response.setNomtire(operation.getNomtire());
        response.setAdressetire(operation.getAdressetire());

        response.setRibbeneficiaire(operation.getRibbeneficiaire());
        response.setIbanbeneficiaire(operation.getIbanbeneficiaire());
        response.setBanquebeneficiaire(operation.getBanquebeneficiaire());
        response.setAgencebeneficiaire(operation.getAgencebeneficiaire());
        response.setNumcomptebeneficiaire(operation.getNumcomptebeneficiaire());
        response.setNombeneficiaire(operation.getNombeneficiaire());
        response.setAdressebeneficiaire(operation.getAdressebeneficiaire());

        response.setMontantoperation(toDecimal(operation.getMontantoperation()));
        response.setDevise(operation.getDevise());
        response.setCodeplace(operation.getCodeplace());
        response.setAgenceremettante(operation.getAgenceremettante());
        response.setDatesaisie(operation.getDatesaisie());
        response.setMotifrejet(operation.getMotifrejet());
        response.setDatepresentation(operation.getDatepresentation());
        response.setDatetraitement(operation.getDatetraitement());
        response.setLibelleoperation(operation.getLibelleoperation());

        response.setIdoperationrejet(operation.getIdoperationrejet());
        response.setRio(operation.getRio());
        response.setIdimageuap(operation.getIdimageuap());
        response.setDatereglement(operation.getDatereglement());
        response.setIdfichiercra(operation.getIdfichiercra());

        response.setIdprelevement(prelevement.getIdprelevement());
        response.setNumeroemetteur(prelevement.getNumeroemetteur());
        response.setDonneesemetteur(prelevement.getDonneesemetteur());
        response.setDateexecution(formatDateForDisplay(prelevement.getDateexecution()));
        response.setDatefacture(formatDateForDisplay(prelevement.getDatefacture()));
        response.setLibelle(prelevement.getLibelle());

        return response;
    }
    
    private String formatDateForDatabase(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
            input.setLenient(false);

            return new SimpleDateFormat("yyyyMMdd").format(input.parse(value));

        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date: " + value, e);
        }
    }

    private String formatDateForDisplay(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(
                    new SimpleDateFormat("yyyyMMdd").parse(value));

        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid stored date: " + value, e);
        }
    }

    private Date parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            format.setLenient(false);
            return format.parse(value);

        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date: " + value, e);
        }
    }

    private BigDecimal toCents(String montant) {
        if (montant == null || montant.isBlank()) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(montant.trim())
                .movePointRight(2)
                .setScale(0, RoundingMode.HALF_UP);
    }

    private String toDecimal(String cents) {
        if (cents == null || cents.isBlank()) {
            return "0.00";
        }

        return new java.math.BigDecimal(cents)
                .movePointLeft(2)
                .toPlainString();
    }
    
    private String toDecimal(BigDecimal montant) {
        if (montant == null) {
            return null;
        }

        return montant.movePointLeft(2).toPlainString();
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private String substringSafe(String value, int start, int end) {
        if (value == null || value.length() < end) {
            throw new IllegalArgumentException(
                    "Value must contain at least " + end + " characters");
        }

        return value.substring(start, end);
    }

    private void savePrelevementEvent(
            Integer operationId,
            String establishment,
            String username,
            String host,
            String initialStatus,
            String finalStatus) {

        try {
            Evenement event = evenementService.addEvenement_CRE(
                    operationId,
                    establishment,
                    username,
                    host,
                    initialStatus,
                    finalStatus
            );

            evenementService.save(event);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to save prelevement event", e);
        }
    }
}