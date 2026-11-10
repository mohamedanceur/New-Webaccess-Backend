package com.Atos.Webaccess.Domain.Virement;


import com.Atos.Webaccess.Domain.Evenement.EvenementService;
import com.Atos.Webaccess.Domain.Operations.Operation;
import com.Atos.Webaccess.Domain.Operations.OperationRepository;
import com.Atos.Webaccess.Domain.SystemeBancaire.SystemeBancaireConfig;
import com.Atos.Webaccess.Domain.SystemeBancaire.SystemeBancaireService;
import com.Atos.Webaccess.User.Entities.User;
import com.Atos.Webaccess.Domain.Virement.DTO.VirementRequest;
import com.Atos.Webaccess.Domain.Virement.DTO.VirementResponse;
import com.Atos.Webaccess.Domain.Virement.operationVirementEnAttente.OperationVirementEnAttente;
import com.Atos.Webaccess.Domain.Virement.operationVirementEnAttente.OperationVirementEnAttenteRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VirementServiceImpl implements VirementService {

    private final VirementRepository virementRepository;
    private final OperationVirementEnAttenteRepository pendingRepository;
    private final EvenementService evenementService;
    private final SystemeBancaireService systemeBancaireService;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Value("${virement.operation.client}")
    private String referenceOperationType;

    @Value("${virement.etat.attente}")
    private Integer pendingState;

    @Value("${virement.etat.valide}")
    private Integer validatedState;

    @Value("${virement.etat.validation.source}")
    private Integer validationSourceState;


    private final OperationRepository operationRepository;



    // =========================================================
    // 1. CLIENT TRANSFER
    // =========================================================

    @Override
    public VirementResponse addVirementCli(
            VirementRequest request,
            User user,
            String host) {

        SystemeBancaireConfig config =
                systemeBancaireService.getConfiguration();

        Integer bankCodeLength = config.getTaillecodebanque();
        Integer finalAgencyCodeLength = config.getTailleFinaleCodeAgence();
        Integer accountNumberLength = config.getTaillenumerocompte();

        String currency = config.getCodedevise();
        String rejectionReason = config.getMotif_rejet_default();

        // Keep the rest of your existing client-transfer logic unchanged.

        validateRequest(request);

        String establishment = getEstablishment(user);

        String reference = generateReference(
                user.getNom(),
                establishment
        );

        OperationVirementEnAttente pending =
                new OperationVirementEnAttente();

        pending.setEtablissement(establishment);
        pending.setTypeoperation(request.getTypVirement());
        pending.setReferenceinterne(reference);

        pending.setNomtire(request.getNomPrenTire());
        pending.setAdressetire(request.getAdrTire());
        pending.setAgencetire(request.getCodeAgenceTire());
        pending.setDatesaisie(new Date());

        pending.setNombeneficiaire(request.getNomPrenBene());
        pending.setAdressebeneficiaire(request.getAdrBene());

        pending.setMontantoperation(toCents(request.getMontant()));

        String beneficiaryAccount =
                request.getCodeAgenceBene()
                + request.getNumCompteBene()
                + request.getCleBene();

        String drawerAccount =
                request.getCodeAgenceTire()
                + request.getNumCompteTire()
                + request.getCleTire();

        if ("1".equals(request.getIndicateurB())) {
            pending.setRibbeneficiaire(beneficiaryAccount);
        } else {
            pending.setIbanbeneficiaire(beneficiaryAccount);
        }

        if ("1".equals(request.getIndicateurT())) {
            pending.setRibtire(drawerAccount);
        } else {
            pending.setIbantire(drawerAccount);
        }

        pending.setEtat(pendingState);
        pending.setOrigine(1);

        pending.setBanquetire(
                request.getCodeAgenceTire().substring(0, bankCodeLength)
        );

        pending.setBanquebeneficiaire(
                request.getCodeAgenceBene().substring(0, bankCodeLength)
        );

        pending.setAgencebeneficiaire(request.getCodeAgenceBene());
        pending.setDevise(currency);
        pending.setAgenceremettante(request.getAgenceRemettante());

        pending.setNumcomptetire(
                extractAccountNumber(
                        drawerAccount,
                        finalAgencyCodeLength,
                        accountNumberLength
                )
        );

        pending.setNumcomptebeneficiaire(
                extractAccountNumber(
                        beneficiaryAccount,
                        finalAgencyCodeLength,
                        accountNumberLength
                )
        );

        pending.setMotifrejet(rejectionReason);
        pending.setLibelleoperation(request.getLibelle());

        OperationVirementEnAttente savedPending =
                pendingRepository.save(pending);

        Virement virement = buildBaseVirement(
                request,
                establishment,
                savedPending.getIdoperation(),
                currency
        );

        Virement savedVirement =
                virementRepository.save(virement);

        savedPending.setIdinstrument(savedVirement.getIdvirement());
        pendingRepository.save(savedPending);

        saveCreationEvent(
                savedPending.getIdoperation(),
                establishment,
                user.getNom(),
                host
        );

        return buildResponse(savedPending, savedVirement);
    }


    // =========================================================
    // 2. BANK-TO-BANK TRANSFER
    // =========================================================

    @Override
    public VirementResponse addVirementBab(
            VirementRequest request,
            User user,
            String host) {

        SystemeBancaireConfig config =
                systemeBancaireService.getConfiguration();

        Integer bankCodeLength = config.getTaillecodebanque();
        Integer accountNumberLength = config.getTaillenumerocompte();
        Integer ribKeyLength = config.getTailleclerib();

        String currency = config.getCodedevise();
        String rejectionReason = config.getMotif_rejet_default();

        validateRequest(request);

        String establishment = getEstablishment(user);

        String reference = generateReference(
                user.getNom(),
                establishment
        );

        String accountNumber = "0".repeat(accountNumberLength);
        String ribKey = "0".repeat(ribKeyLength);

        String drawerRib =
                request.getCodeAgenceTire() + accountNumber + ribKey;

        String beneficiaryRib =
                request.getBanquebeneficiaire()
                + request.getCodeAgenceBene()
                + accountNumber
                + ribKey;

        OperationVirementEnAttente pending =
                new OperationVirementEnAttente();

        pending.setEtablissement(establishment);
        pending.setTypeoperation(request.getTypVirement());
        pending.setReferenceinterne(reference);

        pending.setBanquetire(
                request.getCodeAgenceTire().substring(0, bankCodeLength)
        );

        pending.setAgencetire(request.getCodeAgenceTire());
        pending.setDatesaisie(new Date());

        pending.setBanquebeneficiaire(request.getBanquebeneficiaire());

        pending.setAgencebeneficiaire(
                request.getBanquebeneficiaire()
                + request.getCodeAgenceBene()
        );

        pending.setMontantoperation(toCents(request.getMontant()));

        pending.setEtat(pendingState);
        pending.setOrigine(1);

        pending.setRibtire(drawerRib);
        pending.setRibbeneficiaire(beneficiaryRib);

        pending.setDevise(currency);
        pending.setNumcomptetire(accountNumber);
        pending.setNumcomptebeneficiaire(accountNumber);

        pending.setMotifrejet(rejectionReason);
        pending.setLibelleoperation(request.getLibelle());

        OperationVirementEnAttente savedPending =
                pendingRepository.save(pending);

        Virement virement = buildBaseVirement(
                request,
                establishment,
                savedPending.getIdoperation(),
                currency
        );

        Virement savedVirement =
                virementRepository.save(virement);

        savedPending.setIdinstrument(savedVirement.getIdvirement());
        pendingRepository.save(savedPending);

        saveCreationEvent(
                savedPending.getIdoperation(),
                establishment,
                user.getNom(),
                host
        );

        return buildResponse(savedPending, savedVirement);
    }


    // =========================================================
    // 3. INTERNATIONAL TRANSFER
    // =========================================================

    @Override
    public VirementResponse addVirementEtr(
            VirementRequest request,
            User user,
            String host) {

        SystemeBancaireConfig config =
                systemeBancaireService.getConfiguration();

        Integer bankCodeLength = config.getTaillecodebanque();
        Integer accountNumberLength = config.getTaillenumerocompte();
        Integer ribKeyLength = config.getTailleclerib();

        String currency = config.getCodedevise();
        String rejectionReason = config.getMotif_rejet_default();

        validateRequest(request);

        String establishment = getEstablishment(user);

        String reference = generateReference(
                user.getNom(),
                establishment
        );

        String accountNumber = "0".repeat(accountNumberLength);
        String ribKey = "0".repeat(ribKeyLength);

        String drawerRib =
                request.getCodeAgenceInitOrdre()
                + accountNumber
                + ribKey;

        OperationVirementEnAttente pending =
                new OperationVirementEnAttente();

        pending.setEtablissement(establishment);
        pending.setTypeoperation(request.getTypVirement());
        pending.setReferenceinterne(reference);

        pending.setAgencetire(request.getCodeAgenceInitOrdre());

        pending.setDatesaisie(parseReceptionDate(request.getDateReception()));

        pending.setNombeneficiaire(request.getNomPrenBene());
        pending.setAdressebeneficiaire(request.getAdrBene());

        pending.setMontantoperation(toCents(request.getMontant()));

        pending.setRibbeneficiaire(
                request.getCodeAgenceBene()
                + request.getNumCompteBene()
                + request.getCleBene()
        );

        pending.setEtat(pendingState);
        pending.setOrigine(1);
        pending.setLibelleoperation(request.getLibelle());

        pending.setRibtire(drawerRib);

        pending.setBanquetire(
                request.getCodeAgenceInitOrdre().substring(0, bankCodeLength)
        );

        pending.setNumcomptetire(accountNumber);
        pending.setNumcomptebeneficiaire(request.getNumCompteBene());

        pending.setAgencebeneficiaire(request.getCodeAgenceBene());

        pending.setBanquebeneficiaire(
                request.getCodeAgenceBene().substring(0, bankCodeLength)
        );

        pending.setDevise(currency);
        pending.setAgenceremettante(request.getCodeAgenceInitOrdre());
        pending.setMotifrejet(rejectionReason);

        OperationVirementEnAttente savedPending =
                pendingRepository.save(pending);

        Virement virement = buildBaseVirement(
                request,
                establishment,
                savedPending.getIdoperation(),
                currency
        );

        virement.setMontantinitial(removeSeparators(request.getMontantInitial()));
        virement.setDeviseinitiale(resolveCurrency(
                request.getDeviseInitial(),
                request.getDeviseInitialAutre()
        ));

        virement.setMontantfrais1(removeSeparators(request.getMontantFrais1()));
        virement.setDevisefrais1(resolveCurrency(
                request.getDeviseFrais1(),
                request.getDeviseFrais1Autre()
        ));

        virement.setMontantfrais2(removeSeparators(request.getMontantFrais2()));
        virement.setDevisefrais2(resolveCurrency(
                request.getDeviseFrais2(),
                request.getDeviseFrais2Autre()
        ));

        virement.setMontantfrais3(removeSeparators(request.getMontantFrais3()));
        virement.setDevisefrais3(resolveCurrency(
                request.getDeviseFrais3(),
                request.getDeviseFrais3Autre()
        ));

        virement.setMontantfrais4(removeSeparators(request.getMontantFrais4()));
        virement.setDevisefrais4(resolveCurrency(
                request.getDeviseFrais4(),
                request.getDeviseFrais4Autre()
        ));

        virement.setReferenceinitiale(request.getRefInit());

        Virement savedVirement =
                virementRepository.save(virement);

        savedPending.setIdinstrument(savedVirement.getIdvirement());
        pendingRepository.save(savedPending);

        saveCreationEvent(
                savedPending.getIdoperation(),
                establishment,
                user.getNom(),
                host
        );

        return buildResponse(savedPending, savedVirement);
    }


    // =========================================================
    // PRIVATE METHODS
    // =========================================================

    private Virement buildBaseVirement(
            VirementRequest request,
            String establishment,
            Integer pendingOperationId,
            String currency) {

        Virement virement = new Virement();

        virement.setEtablissemenet(establishment);

        String internalNumber = request.getNumInterne();

        virement.setNumerointerne(
                internalNumber == null || internalNumber.isBlank()
                        ? "0000000000"
                        : internalNumber
        );

        virement.setNaturedesfonds(request.getNatFond());
        virement.setMontantinitial(removeSeparators(request.getMontant()));
        virement.setDeviseinitiale(currency);
        virement.setIdoperation(pendingOperationId);

        return virement;
    }


    private String generateReference(
            String username,
            String establishment) {

        String formattedName = username == null ? "" : username.trim();

        if (formattedName.length() > 8) {
            formattedName = formattedName.substring(0, 8);
        } else {
            formattedName = String.format("%8s", formattedName);
        }

        Number sequence = (Number) entityManager
                .createNativeQuery(
                        "SELECT NEXT VALUE FOR dbo.GEN_ADDOPR"
                )
                .getSingleResult();

        String sequenceNumber = String.format(
                "%08d",
                sequence.longValue()
        );

        String date = new SimpleDateFormat("yyyyMMdd")
                .format(new Date());

        return formattedName
                + referenceOperationType
                + "Mwa"
                + "00"
                + establishment
                + "00000"
                + "00"
                + date
                + sequenceNumber;
    }


    private String toCents(String value) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Amount is required");
        }

        BigDecimal amount = new BigDecimal(
                value.replace(" ", "").replace(",", ".")
        );

        return amount
                .movePointRight(2)
                .setScale(0, RoundingMode.DOWN)
                .toPlainString();
    }


    private String removeSeparators(String value) {

        return value == null
                ? null
                : value.replace(",", "").replace(".", "");
    }


    private String resolveCurrency(String selected, String other) {

        return selected == null || selected.isBlank()
                ? other
                : selected;
    }


    private String extractAccountNumber(
            String account,
            Integer agencyLength,
            Integer accountLength) {

        return account.substring(
                agencyLength,
                agencyLength + accountLength
        );
    }


    private Date parseReceptionDate(String value) {

        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(value);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(
                    "Invalid reception date: " + value,
                    ex
            );
        }
    }


    private String getEstablishment(User user) {

        if (user == null || user.getBank() == null
                || user.getBank().getCode() == null) {
            throw new IllegalStateException(
                    "The authenticated user has no associated bank"
            );
        }

        return user.getBank().getCode();
    }


    private void validateRequest(VirementRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("Virement request is required");
        }

        if (request.getTypVirement() == null
                || request.getTypVirement().isBlank()) {
            throw new IllegalArgumentException("Transfer type is required");
        }

        if (request.getMontant() == null
                || request.getMontant().isBlank()) {
            throw new IllegalArgumentException("Amount is required");
        }
    }


    private void saveCreationEvent(
            Integer operationId,
            String establishment,
            String username,
            String host) {

        try {
            evenementService.save(
                    evenementService.addEvenement_CRE(
                            operationId,
                            establishment,
                            username,
                            host,
                            "0",
                            String.valueOf(pendingState)
                    )
            );
        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Failed to create virement event",
                    ex
            );
        }
    }


    private VirementResponse buildResponse(
            OperationVirementEnAttente pending,
            Virement virement) {

        VirementResponse response = new VirementResponse();

        response.setIdoperation(pending.getIdoperation());
        response.setTypeoperation(pending.getTypeoperation());
        response.setIdinstrument(virement.getIdvirement());
        response.setRibtire(pending.getRibtire());
        response.setRibbeneficiaire(pending.getRibbeneficiaire());
        response.setMontantoperation(pending.getMontantoperation());
        response.setRio(pending.getRio());
        response.setNumerointerne(virement.getNumerointerne());

        return response;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VirementResponse> getPendingVirements() {

        List<VirementResponse> results = new ArrayList<>();

        List<OperationVirementEnAttente> pendingOperations =
                pendingRepository.findAll();

        for (OperationVirementEnAttente pending : pendingOperations) {

            String type = pending.getTypeoperation();

            // Equivalent to SQL LIKE '_1_'
            if (type == null || !type.matches(".1.")) {
                continue;
            }

            if (pending.getIdinstrument() == null) {
                continue;
            }

            Virement virement = virementRepository
                    .findById(pending.getIdinstrument())
                    .orElse(null);

            // Legacy query uses an inner join
            if (virement != null) {
                results.add(buildResponse(pending, virement));
            }
        }

        return results;
    }
    
    @Override
    public int validateVirements(
            List<Integer> pendingIds,
            User user,
            String host) {

        if (pendingIds == null || pendingIds.isEmpty()) {
            return 0;
        }

        String establishment = getEstablishment(user);
        String username = user.getNom();

        for (Integer pendingId : pendingIds) {

            OperationVirementEnAttente pending =
                    pendingRepository.findById(pendingId).orElse(null);

            if (pending == null) {
                throw new IllegalArgumentException(
                        "Pending virement not found: " + pendingId
                );
            }

            // Check whether this reference already exists
            boolean alreadyExists = operationRepository
                    .findByReferenceinterne(pending.getReferenceinterne())
                    .isPresent();

            if (alreadyExists) {
                pendingRepository.delete(pending);
                continue;
            }

            // Create the validated operation
            Operation operation = new Operation();

            operation.setEtablissement(pending.getEtablissement());
            operation.setIdinstrument(pending.getIdinstrument());
            operation.setTypeoperation(pending.getTypeoperation());
            operation.setReferenceinterne(pending.getReferenceinterne());

            operation.setNomtire(pending.getNomtire());
            operation.setAdressetire(pending.getAdressetire());
            operation.setAgencetire(pending.getAgencetire());
            operation.setDatesaisie(pending.getDatesaisie());

            operation.setNombeneficiaire(pending.getNombeneficiaire());
            operation.setAdressebeneficiaire(pending.getAdressebeneficiaire());

            operation.setMontantoperation(pending.getMontantoperation());

            operation.setRibtire(pending.getRibtire());
            operation.setRibbeneficiaire(pending.getRibbeneficiaire());

            operation.setEtat(validatedState);
            operation.setOrigine(pending.getOrigine());

            operation.setBanquetire(pending.getBanquetire());
            operation.setBanquebeneficiaire(pending.getBanquebeneficiaire());
            operation.setAgencebeneficiaire(pending.getAgencebeneficiaire());

            operation.setDevise(pending.getDevise());
            operation.setAgenceremettante(pending.getAgenceremettante());

            operation.setNumcomptetire(pending.getNumcomptetire());
            operation.setNumcomptebeneficiaire(
                    pending.getNumcomptebeneficiaire()
            );

            operation.setMotifrejet(pending.getMotifrejet());
            operation.setLibelleoperation(pending.getLibelleoperation());

            operation.setIndicateurannulation("N");
            operation.setIndicateurrejet("N");
            operation.setIndicateurrepresentation("N");

            // Save in OPERATIONS
            Operation savedOperation = operationRepository.save(operation);

            // Find and update the associated VIREMENTS record
            Virement virement = virementRepository
                    .findById(pending.getIdinstrument())
                    .orElseThrow(() -> new IllegalStateException(
                            "Virement not found for pending operation: " + pendingId
                    ));

            virement.setIdoperation(savedOperation.getIdoperation());
            virementRepository.save(virement);

            // Create validation event
         // Create validation event
            saveValidationEvent(
                    savedOperation.getIdoperation(),
                    establishment,
                    username,
                    host
            );

            // Remove pending operation
            pendingRepository.delete(pending);
        }

        return 1;
    }
    
    @Override
    public int deleteVirement(
            Integer virementId,
            Integer pendingOperationId) {

        Virement virement = virementRepository
                .findById(virementId)
                .orElse(null);

        OperationVirementEnAttente pending = pendingRepository
                .findById(pendingOperationId)
                .orElse(null);

        if (virement == null || pending == null) {
            return 0;
        }

        virementRepository.delete(virement);
        pendingRepository.delete(pending);

        return 1;
    }
    
    private void saveValidationEvent(
            Integer operationId,
            String establishment,
            String username,
            String host) {

        try {
            evenementService.save(
                    evenementService.addEvenement_CRE(
                            operationId,
                            establishment,
                            username,
                            host,
                            String.valueOf(validationSourceState),
                            String.valueOf(validatedState)
                    )
            );
        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Failed to create virement validation event",
                    ex
            );
        }
    }
}