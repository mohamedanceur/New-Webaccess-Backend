package com.Atos.Webaccess.Domain.Carte;


import com.Atos.Webaccess.Domain.Evenement.EvenementService;
import com.Atos.Webaccess.Domain.Operations.Operation;
import com.Atos.Webaccess.Domain.Carte.DTO.CarteRequest;
import com.Atos.Webaccess.Domain.Carte.DTO.CarteResponse;
import com.Atos.Webaccess.Domain.Operations.OperationRepository;
import com.Atos.Webaccess.Domain.Carte.opCarteEnAttente.OpCarteEnAttente;
import com.Atos.Webaccess.Domain.Carte.opCarteEnAttente.OpCarteEnAttenteRepository;
import com.Atos.Webaccess.Domain.Libelle.Libelle;
import com.Atos.Webaccess.Domain.Libelle.LibelleRepository;
import java.util.Map;
import java.util.stream.Collectors;
import com.Atos.Webaccess.User.Entities.User;

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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CarteServiceImpl implements CarteService {

    private final CarteRepository carteRepository;
    private final OpCarteEnAttenteRepository pendingRepository;
    private final OperationRepository operationRepository;
    private final EvenementService evenementService;
    private final LibelleRepository libelleRepository;
    @PersistenceContext
    private EntityManager entityManager;

    // Legacy configuration values from param.xml

    @Value("${carte.retrait:4}")
    private String carteRetrait;

    @Value("${carte.paiement:5}")
    private String cartePaiement;

    @Value("${carte.operation.gab:040}")
    private String operationGAB;

    @Value("${carte.operation.tpe:050}")
    private String operationTPE;

    @Value("${carte.operation.remboursement:051}")
    private String operationRemboursementTPE;

    @Value("${carte.etat.attente:6}")
    private Integer etatAttente;

    @Value("${carte.etat.valide:3}")
    private Integer etatValide;

    @Value("${carte.devise:DZD}")
    private String defaultCurrency;


    // =========================================================
    // 1. LIST PENDING CARD OPERATIONS
    // =========================================================


	@Override
	@Transactional(readOnly = true)
	public List<CarteResponse> getPendingOperations() {
	
	    Map<String, Map<String, String>> labels = loadCarteLabels();
	
	    Map<String, String> origins =
	            labels.getOrDefault("ORIGINE", Map.of());
	
	    Map<String, String> operationTypes =
	            labels.getOrDefault("CODEOPERATION", Map.of());
	
	    Map<String, String> states =
	            labels.getOrDefault("ETAT", Map.of());
	
	    List<Object[]> rows = pendingRepository.findPendingCardOperations(
	    		"_" + carteRetrait + "_",
	    		"_" + cartePaiement + "_"
	    );
	
	    return rows.stream().map(row -> {
	
	        CarteResponse response = new CarteResponse();
	
	        response.setIdoperation((Integer) row[0]);
	        response.setNumerocarte((String) row[1]);
	        response.setBanquetire((String) row[2]);
	        response.setMontantoperation(toDecimal((Number) row[3]));
	
	        response.setOrigine((Integer) row[4]);
	        response.setRio((String) row[5]);
	        response.setReferenceinterne((String) row[6]);
	        response.setTypeoperation((String) row[7]);
	        response.setEtat((Integer) row[8]);
	        response.setIdopcarte((Integer) row[9]);
	
	        // Resolve legacy labels, preserving the original code as fallback.
	        response.setLibelleorigine(
	                origins.getOrDefault(
	                        String.valueOf(response.getOrigine()),
	                        String.valueOf(response.getOrigine())
	                )
	        );
	
	        response.setLibelletypeoperation(
	                operationTypes.getOrDefault(
	                        response.getTypeoperation(),
	                        response.getTypeoperation()
	                )
	        );
	
	        response.setLibelleetat(
	                states.getOrDefault(
	                        String.valueOf(response.getEtat()),
	                        String.valueOf(response.getEtat())
	                )
	        );
	
	        return response;
	
	    }).toList();
	}



    // =========================================================
    // 2. CREATE GAB OPERATION
    // =========================================================

    @Override
    public CarteResponse addCarteGAB(
            CarteRequest request,
            User user,
            String name,
            String host,
            String currency) {

        validateCommonRequest(request);

        String establishment = getEstablishment(user);

        String reference = generateReference(
                name,
                operationGAB,
                establishment
        );

        Carte carte = new Carte();

        carte.setIdoperation(0);

        carte.setEtablissement(establishment);
        carte.setBin(request.getNumerocarte().substring(0, 6));

        String amount = toCents(request.getMontantretrait());

        carte.setMontant(amount);

        // Preserved from the legacy implementation.
        carte.setComission(amount);

        carte.setNumerotransaction(request.getNumerotransaction());
        carte.setSystemeacquereur(request.getIdSystemRetrait());
        carte.setPointacquereur(request.getIdPointRetrait());
        carte.setModelecturecarte(request.getModelecturecarte());
        carte.setModeauthentification(request.getModeauthentification());
        carte.setNumeroautorisation(request.getNumeroautorisation());

        carte.setDatedebutvaliditeapplication(
                removeSlashes(request.getDatedebutvaliditeapplication())
        );

        carte.setDatefinvaliditeapplication(
                removeSlashes(request.getDatefinvaliditeapplication())
        );

        carte.setDonneesemv(request.getDonneesemv());
        carte.setNumerocarte(request.getNumerocarte());
        carte.setTypecommission(request.getTypecommission());

        Carte savedCarte = carteRepository.save(carte);

        OpCarteEnAttente pending = new OpCarteEnAttente();

        pending.setEtablissement(establishment);
        pending.setIdinstrument(savedCarte.getIdopcarte());
        pending.setTypeoperation(operationGAB);
        pending.setReferenceinterne(reference);

        pending.setAgencebeneficiaire(
                establishment + request.getCodeAgenceAcqBen()
        );

        pending.setDatesaisie(parseDateTime(
                request.getDate_retrait(),
                request.getHeureRetrait()
        ));

        pending.setMontantoperation(
                Long.parseLong(toCents(request.getMontantOpComp()))
        );

        pending.setEtat(etatAttente);
        pending.setOrigine(1);
        pending.setBanquebeneficiaire(establishment);
        pending.setMotifrejet("MC000");
        pending.setDevise(currency);

        String beneficiaryAgency =
                establishment + request.getCodeAgenceAcqBen();

        String drawerAgency =
                request.getCodeBanqueTire() + request.getCodeAgenceTire();

        pending.setAgenceremettante(beneficiaryAgency);
        pending.setBanquetire(request.getCodeBanqueTire());
        pending.setAgencetire(drawerAgency);

        pending.setRibtire(drawerAgency + "000000000097");
        pending.setRibbeneficiaire(beneficiaryAgency + "000000000097");

        pending.setNumcomptetire("0000000000");
        pending.setNumcomptebeneficiaire("0000000000");

        OpCarteEnAttente savedPending =
                pendingRepository.save(pending);

        savedCarte.setIdoperation(savedPending.getIdoperation());
        carteRepository.save(savedCarte);

        saveCreationEvent(
                savedPending.getIdoperation(),
                establishment,
                name,
                host
        );

        return buildResponse(savedPending, savedCarte);
    }


    // =========================================================
    // 3. CREATE TPE / REFUND OPERATION
    // =========================================================

    @Override
    public CarteResponse addCarteTPE(
            CarteRequest request,
            User user,
            String name,
            String host,
            String currency) {

        validateCommonRequest(request);

        String establishment = getEstablishment(user);

        String typeOperation = request.getTypeoperation();

        if (!operationTPE.equals(typeOperation)
                && !operationRemboursementTPE.equals(typeOperation)) {
            throw new IllegalArgumentException(
                    "Unsupported TPE operation type"
            );
        }

        String reference = generateReference(
                name,
                typeOperation,
                establishment
        );

        Carte carte = new Carte();

        carte.setIdoperation(0);
        carte.setEtablissement(establishment);

        carte.setTypepaiement(request.getTypepaiement());
        carte.setTelephonecommercant(request.getTelephonecommercant());
        carte.setNumerocontrataccepteur(
                request.getNumerocontrataccepteur()
        );

        carte.setNumerotransaction(request.getNumerotransaction());
        carte.setCodeactivite(request.getCodeactivite());
        carte.setNumerocarte(request.getNumerocarte());

        carte.setMontant(toCents(request.getMontantOpPaiement()));
        carte.setSensoperation(request.getSensoperation());

        carte.setComission(toCents(request.getMontantComission()));

        carte.setDateremise(formatDateForDatabase(request.getDateremise()));

        carte.setModelecturecarte(request.getModelecturecarte());
        carte.setModeauthentification(request.getModeauthentification());
        carte.setNumeroautorisation(request.getNumeroautorisation());

        carte.setDatedebutvaliditeapplication(
                removeSlashes(request.getDatedebutvaliditeapplication())
        );

        carte.setDatefinvaliditeapplication(
                removeSlashes(request.getDatefinvaliditeapplication())
        );

        carte.setDonneesemv(request.getDonneesemv());

        carte.setBin(request.getNumerocarte().substring(0, 6));
        carte.setTypecommission(request.getTypecommission());

        carte.setSystemeacquereur(request.getIdSystemAccept());
        carte.setPointacquereur(request.getIdPointAccept());

        Carte savedCarte = carteRepository.save(carte);

        OpCarteEnAttente pending = new OpCarteEnAttente();

        pending.setEtablissement(establishment);
        pending.setIdinstrument(savedCarte.getIdopcarte());
        pending.setTypeoperation(typeOperation);
        pending.setReferenceinterne(reference);

        pending.setAgencebeneficiaire(request.getAgencebeneficiaire());

        pending.setDatesaisie(parseDateTime(
                request.getDate_paiement(),
                request.getHeurePaiement()
        ));

        pending.setMontantoperation(
                Long.parseLong(toCents(request.getMontantACompenser()))
        );

        pending.setEtat(etatAttente);
        pending.setOrigine(1);

        pending.setBanquebeneficiaire(
                request.getAgencebeneficiaire().substring(0, 3)
        );

        pending.setMotifrejet("MC000");
        pending.setDevise(currency);

        pending.setAgenceremettante(request.getAgencebeneficiaire());

        String drawerAgency = request.getCodeBanqueTire() + "00000";

        pending.setBanquetire(request.getCodeBanqueTire());
        pending.setAgencetire(drawerAgency);

        pending.setRibtire(drawerAgency + "000000000097");

        pending.setRibbeneficiaire(
                request.getAgencebeneficiaire()
                        + request.getNumcomptebeneficiaire()
                        + request.getClebeneficiaire()
        );

        pending.setNumcomptetire("0000000000");
        pending.setNumcomptebeneficiaire(
                request.getNumcomptebeneficiaire()
        );

        pending.setNombeneficiaire(request.getNomComm());
        pending.setAdressebeneficiaire(request.getAdrComm());

        OpCarteEnAttente savedPending =
                pendingRepository.save(pending);

        savedCarte.setIdoperation(savedPending.getIdoperation());
        carteRepository.save(savedCarte);

        saveCreationEvent(
                savedPending.getIdoperation(),
                establishment,
                name,
                host
        );

        return buildResponse(savedPending, savedCarte);
    }
    
    @Override
    public CarteResponse addCarteRefundTPE(
            CarteRequest request,
            User user,
            String name,
            String host,
            String currency) {

        request.setTypeoperation(operationRemboursementTPE);

        return addCarteTPE(
                request,
                user,
                name,
                host,
                currency
        );
    }
    
    @Override
    public CarteResponse modifyCarteRefundTPE(
            Integer idOperation,
            CarteRequest request,
            User user,
            String currency) {

        request.setTypeoperation(operationRemboursementTPE);

        return modifyCarteTPE(
                idOperation,
                request,
                user,
                currency
        );
    }


    // =========================================================
    // 4. VALIDATE PENDING OPERATIONS
    // =========================================================

    @Override
    public void validateOperations(
            List<Integer> operationIds,
            User user,
            String host) {

        if (operationIds == null || operationIds.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one operation must be selected"
            );
        }

        String establishment = getEstablishment(user);
        String name = user.getNom();

        for (Integer id : operationIds) {

            OpCarteEnAttente pending = pendingRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Pending card operation not found: " + id
                    ));

            Optional<Operation> existing =
                    operationRepository.findByReferenceinterne(
                            pending.getReferenceinterne()
                    );

            if (existing.isPresent()) {
                // Legacy behavior: remove duplicate pending operation.
                pendingRepository.delete(pending);
                continue;
            }

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
            operation.setAdressebeneficiaire(
                    pending.getAdressebeneficiaire()
            );

            operation.setMontantoperation(
                    String.valueOf(pending.getMontantoperation())
            );

            operation.setRibtire(pending.getRibtire());
            operation.setRibbeneficiaire(pending.getRibbeneficiaire());

            operation.setEtat(etatValide);
            operation.setOrigine(pending.getOrigine());

            operation.setBanquetire(pending.getBanquetire());
            operation.setBanquebeneficiaire(
                    pending.getBanquebeneficiaire()
            );

            operation.setAgencebeneficiaire(
                    pending.getAgencebeneficiaire()
            );

            operation.setDevise(pending.getDevise());
            operation.setAgenceremettante(
                    pending.getAgenceremettante()
            );

            operation.setNumcomptetire(pending.getNumcomptetire());
            operation.setNumcomptebeneficiaire(
                    pending.getNumcomptebeneficiaire()
            );

            operation.setMotifrejet(pending.getMotifrejet());
            operation.setLibelleoperation(pending.getLibelleoperation());

            operation.setIndicateurannulation("N");
            operation.setIndicateurrejet("N");
            operation.setIndicateurrepresentation("N");

            Operation savedOperation =
                    operationRepository.save(operation);

            Carte carte = carteRepository
                    .findByIdoperationAndIdopcarte(
                            pending.getIdoperation(),
                            pending.getIdinstrument()
                    )
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Associated card not found"
                    ));

            carte.setIdoperation(savedOperation.getIdoperation());

            carteRepository.save(carte);

            saveCreationEvent(
                    savedOperation.getIdoperation(),
                    establishment,
                    name,
                    host
            );

            pendingRepository.delete(pending);
        }
    }


    // =========================================================
    // 5. GET GAB OPERATION FOR MODIFICATION
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public CarteRequest getCarteGABForModification(
            Integer idOperation,
            Integer idCarte,
            Integer bankCodeLength,
            Integer agencyCodeLength) {

        Carte carte = findCard(idOperation, idCarte);

        OpCarteEnAttente pending = findPending(idOperation);

        CarteRequest request = new CarteRequest();

        request.setIdoperation(idOperation);
        request.setIdopcarte(idCarte);
        request.setTypeoperation(pending.getTypeoperation());

        request.setNumerocarte(carte.getNumerocarte());
        request.setNumerotransaction(carte.getNumerotransaction());

        request.setModelecturecarte(carte.getModelecturecarte());
        request.setModeauthentification(
                carte.getModeauthentification()
        );

        request.setNumeroautorisation(carte.getNumeroautorisation());

        request.setDatedebutvaliditeapplication(
                formatCardDate(carte.getDatedebutvaliditeapplication())
        );

        request.setDatefinvaliditeapplication(
                formatCardDate(carte.getDatefinvaliditeapplication())
        );

        request.setDonneesemv(carte.getDonneesemv());
        request.setTypecommission(carte.getTypecommission());

        request.setIdSystemRetrait(carte.getSystemeacquereur());
        request.setIdPointRetrait(carte.getPointacquereur());

        request.setMontantretrait(fromCents(carte.getMontant()));
        request.setMontantComission(fromCents(carte.getComission()));

        String drawerAgency = pending.getAgencetire();

        if (drawerAgency != null
                && drawerAgency.length() == bankCodeLength + agencyCodeLength) {

            request.setCodeBanqueTire(
                    drawerAgency.substring(0, bankCodeLength)
            );

            request.setCodeAgenceTire(
                    drawerAgency.substring(
                            bankCodeLength,
                            bankCodeLength + agencyCodeLength
                    )
            );
        }

        request.setCodeAgenceAcqBen(
                extractAgencyCode(pending.getAgencebeneficiaire())
        );

        request.setDate_retrait(
                formatDate(pending.getDatesaisie(), "dd/MM/yyyy")
        );

        request.setHeureRetrait(
                formatDate(pending.getDatesaisie(), "HH:mm:ss")
        );

        request.setMontantOpComp(
                fromCents(pending.getMontantoperation())
        );

        //request.setReferenceinterne(null);

        return request;
    }


    // =========================================================
    // 6. GET TPE OPERATION FOR MODIFICATION
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public CarteRequest getCarteTPEForModification(
            Integer idOperation,
            Integer idCarte,
            Integer finalAgencyCodeLength,
            Integer accountNumberLength,
            Integer ribKeyLength) {

        Carte carte = findCard(idOperation, idCarte);

        OpCarteEnAttente pending = findPending(idOperation);

        CarteRequest request = new CarteRequest();

        request.setIdoperation(idOperation);
        request.setIdopcarte(idCarte);
        request.setTypeoperation(pending.getTypeoperation());

        request.setTypepaiement(carte.getTypepaiement());
        request.setTelephonecommercant(
                carte.getTelephonecommercant()
        );

        request.setNumerocontrataccepteur(
                carte.getNumerocontrataccepteur()
        );

        request.setNumerotransaction(carte.getNumerotransaction());
        request.setCodeactivite(carte.getCodeactivite());
        request.setNumerocarte(carte.getNumerocarte());

        request.setMontantOpPaiement(fromCents(carte.getMontant()));
        request.setSensoperation(carte.getSensoperation());
        request.setMontantComission(fromCents(carte.getComission()));

        request.setDateremise(
                formatCardDate(carte.getDateremise())
        );

        request.setModelecturecarte(carte.getModelecturecarte());
        request.setModeauthentification(
                carte.getModeauthentification()
        );

        request.setNumeroautorisation(carte.getNumeroautorisation());

        request.setDatedebutvaliditeapplication(
                formatCardDate(carte.getDatedebutvaliditeapplication())
        );

        request.setDatefinvaliditeapplication(
                formatCardDate(carte.getDatefinvaliditeapplication())
        );

        request.setDonneesemv(carte.getDonneesemv());
        request.setTypecommission(carte.getTypecommission());

        request.setIdSystemAccept(carte.getSystemeacquereur());
        request.setIdPointAccept(carte.getPointacquereur());

        request.setCodeBanqueTire(pending.getBanquetire());
        request.setAgencebeneficiaire(pending.getAgencebeneficiaire());

        request.setDate_paiement(
                formatDate(pending.getDatesaisie(), "dd/MM/yyyy")
        );

        request.setHeurePaiement(
                formatDate(pending.getDatesaisie(), "HH:mm:ss")
        );

        request.setMontantACompenser(
                fromCents(pending.getMontantoperation())
        );

        String rib = pending.getRibbeneficiaire();

        if (rib != null
                && rib.length() == finalAgencyCodeLength
                        + accountNumberLength
                        + ribKeyLength) {

            request.setAgencebeneficiaire(
                    rib.substring(0, finalAgencyCodeLength)
            );

            request.setNumcomptebeneficiaire(
                    rib.substring(
                            finalAgencyCodeLength,
                            finalAgencyCodeLength + accountNumberLength
                    )
            );

            request.setClebeneficiaire(
                    rib.substring(
                            finalAgencyCodeLength + accountNumberLength
                    )
            );
        }

        request.setNomComm(pending.getNombeneficiaire());
        request.setAdrComm(pending.getAdressebeneficiaire());

        return request;
    }


    // =========================================================
    // 7. MODIFY GAB OPERATION
    // =========================================================

    @Override
    public CarteResponse modifyCarteGAB(
            Integer idOperation,
            CarteRequest request,
            User user,
            String currency) {

        String establishment = getEstablishment(user);

        Carte carte = findCard(idOperation, request.getIdopcarte());
        OpCarteEnAttente pending = findPending(idOperation);

        validateCommonRequest(request);

        carte.setEtablissement(establishment);
        carte.setBin(request.getNumerocarte().substring(0, 6));

        String amount = toCents(request.getMontantretrait());

        carte.setMontant(amount);
        carte.setComission(amount);

        carte.setNumerotransaction(request.getNumerotransaction());
        carte.setSystemeacquereur(request.getIdSystemRetrait());
        carte.setPointacquereur(request.getIdPointRetrait());

        carte.setModelecturecarte(request.getModelecturecarte());
        carte.setModeauthentification(request.getModeauthentification());
        carte.setNumeroautorisation(request.getNumeroautorisation());

        carte.setDatedebutvaliditeapplication(
                removeSlashes(request.getDatedebutvaliditeapplication())
        );

        carte.setDatefinvaliditeapplication(
                removeSlashes(request.getDatefinvaliditeapplication())
        );

        carte.setDonneesemv(request.getDonneesemv());
        carte.setNumerocarte(request.getNumerocarte());
        carte.setTypecommission(request.getTypecommission());

        carteRepository.save(carte);

        updatePendingGAB(
                pending,
                request,
                establishment,
                currency
        );

        pendingRepository.save(pending);

        return buildResponse(pending, carte);
    }


    // =========================================================
    // 8. MODIFY TPE OPERATION
    // =========================================================

    @Override
    public CarteResponse modifyCarteTPE(
            Integer idOperation,
            CarteRequest request,
            User user,
            String currency) {

        String establishment = getEstablishment(user);

        Carte carte = findCard(idOperation, request.getIdopcarte());
        OpCarteEnAttente pending = findPending(idOperation);

        validateCommonRequest(request);

        carte.setEtablissement(establishment);

        carte.setTypepaiement(request.getTypepaiement());
        carte.setTelephonecommercant(request.getTelephonecommercant());

        carte.setNumerocontrataccepteur(
                request.getNumerocontrataccepteur()
        );

        carte.setNumerotransaction(request.getNumerotransaction());
        carte.setCodeactivite(request.getCodeactivite());
        carte.setNumerocarte(request.getNumerocarte());

        carte.setMontant(toCents(request.getMontantOpPaiement()));
        carte.setSensoperation(request.getSensoperation());

        carte.setComission(toCents(request.getMontantComission()));

        carte.setDateremise(formatDateForDatabase(request.getDateremise()));

        carte.setModelecturecarte(request.getModelecturecarte());
        carte.setModeauthentification(request.getModeauthentification());
        carte.setNumeroautorisation(request.getNumeroautorisation());

        carte.setDatedebutvaliditeapplication(
                removeSlashes(request.getDatedebutvaliditeapplication())
        );

        carte.setDatefinvaliditeapplication(
                removeSlashes(request.getDatefinvaliditeapplication())
        );

        carte.setDonneesemv(request.getDonneesemv());
        carte.setBin(request.getNumerocarte().substring(0, 6));
        carte.setTypecommission(request.getTypecommission());

        carte.setSystemeacquereur(request.getIdSystemAccept());
        carte.setPointacquereur(request.getIdPointAccept());

        carteRepository.save(carte);

        updatePendingTPE(
                pending,
                request,
                establishment,
                currency
        );

        pendingRepository.save(pending);

        return buildResponse(pending, carte);
    }


    // =========================================================
    // PRIVATE METHODS
    // =========================================================

    private void updatePendingGAB(
            OpCarteEnAttente pending,
            CarteRequest request,
            String establishment,
            String currency) {

        String beneficiaryAgency =
                establishment + request.getCodeAgenceAcqBen();

        String drawerAgency =
                request.getCodeBanqueTire() + request.getCodeAgenceTire();

        pending.setEtablissement(establishment);
        pending.setIdinstrument(request.getIdopcarte());
        pending.setTypeoperation(operationGAB);

        pending.setAgencebeneficiaire(beneficiaryAgency);

        pending.setDatesaisie(parseDateTime(
                request.getDate_retrait(),
                request.getHeureRetrait()
        ));

        pending.setMontantoperation(
                Long.parseLong(toCents(request.getMontantOpComp()))
        );

        pending.setEtat(etatAttente);
        pending.setOrigine(1);
        pending.setBanquebeneficiaire(establishment);
        pending.setMotifrejet("MC000");
        pending.setDevise(currency);

        pending.setAgenceremettante(beneficiaryAgency);
        pending.setBanquetire(request.getCodeBanqueTire());
        pending.setAgencetire(drawerAgency);

        pending.setRibtire(drawerAgency + "000000000097");
        pending.setRibbeneficiaire(beneficiaryAgency + "000000000097");

        pending.setNumcomptetire("0000000000");
        pending.setNumcomptebeneficiaire("0000000000");
    }


    private void updatePendingTPE(
            OpCarteEnAttente pending,
            CarteRequest request,
            String establishment,
            String currency) {

        String drawerAgency = request.getCodeBanqueTire() + "00000";

        pending.setEtablissement(establishment);
        pending.setIdinstrument(request.getIdopcarte());
        pending.setTypeoperation(request.getTypeoperation());

        pending.setAgencebeneficiaire(request.getAgencebeneficiaire());

        pending.setDatesaisie(parseDateTime(
                request.getDate_paiement(),
                request.getHeurePaiement()
        ));

        pending.setMontantoperation(
                Long.parseLong(toCents(request.getMontantACompenser()))
        );

        pending.setEtat(etatAttente);
        pending.setOrigine(1);

        pending.setBanquebeneficiaire(
                request.getAgencebeneficiaire().substring(0, 3)
        );

        pending.setMotifrejet("MC000");
        pending.setDevise(currency);

        pending.setAgenceremettante(request.getAgencebeneficiaire());

        pending.setBanquetire(request.getCodeBanqueTire());
        pending.setAgencetire(drawerAgency);

        pending.setRibtire(drawerAgency + "000000000097");

        pending.setRibbeneficiaire(
                request.getAgencebeneficiaire()
                        + request.getNumcomptebeneficiaire()
                        + request.getClebeneficiaire()
        );

        pending.setNumcomptetire("0000000000");
        pending.setNumcomptebeneficiaire(
                request.getNumcomptebeneficiaire()
        );

        pending.setNombeneficiaire(request.getNomComm());
        pending.setAdressebeneficiaire(request.getAdrComm());
    }


    private Carte findCard(Integer idOperation, Integer idCarte) {

        return carteRepository
                .findByIdoperationAndIdopcarte(idOperation, idCarte)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Card not found"
                ));
    }


    private OpCarteEnAttente findPending(Integer idOperation) {

        return pendingRepository.findById(idOperation)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Pending card operation not found"
                ));
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


    private String generateReference(
            String name,
            String typeOperation,
            String establishment) {

        String formattedName = name == null ? "" : name.trim();

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
                + typeOperation
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
            throw new IllegalArgumentException(
                    "Amount is required"
            );
        }

        String normalized = value
                .replace(" ", "")
                .replace(",", ".")
                .replace(";", ".");

        try {
            BigDecimal amount = new BigDecimal(normalized);

            return amount
                    .movePointRight(2)
                    .setScale(0, RoundingMode.UNNECESSARY)
                    .toPlainString();

        } catch (NumberFormatException | ArithmeticException ex) {
            throw new IllegalArgumentException(
                    "Invalid amount: " + value,
                    ex
            );
        }
    }


    private String fromCents(Number value) {

        if (value == null) {
            return "0.00";
        }

        return new BigDecimal(value.toString())
                .movePointLeft(2)
                .setScale(2, RoundingMode.UNNECESSARY)
                .toPlainString();
    }
    
    private String fromCents(String value) {

        if (value == null || value.isBlank()) {
            return "0.00";
        }

        return new BigDecimal(value)
                .movePointLeft(2)
                .setScale(2, RoundingMode.UNNECESSARY)
                .toPlainString();
    }


    private BigDecimal toDecimal(Number value) {

        if (value == null) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(value.toString())
                .movePointLeft(2)
                .setScale(2, RoundingMode.UNNECESSARY);
    }


    private String removeSlashes(String value) {

        return value == null ? null : value.replace("/", "");
    }


    private String formatDateForDatabase(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy")
                    .parse(value);

            return new SimpleDateFormat("yyyyMMdd").format(date);

        } catch (ParseException ex) {
            throw new IllegalArgumentException(
                    "Invalid date: " + value,
                    ex
            );
        }
    }


    private String formatCardDate(String value) {

        if (value == null || value.length() != 8) {
            return value;
        }

        return value.substring(0, 2)
                + "/"
                + value.substring(2, 4)
                + "/"
                + value.substring(4, 8);
    }


    private Date parseDateTime(String date, String time) {

        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    .parse(date + " " + time);

        } catch (ParseException ex) {
            throw new IllegalArgumentException(
                    "Invalid operation date or time",
                    ex
            );
        }
    }


    private String formatDate(Date date, String pattern) {

        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(pattern).format(date);
    }


    private String extractAgencyCode(String agency) {

        if (agency == null) {
            return null;
        }

        String establishment = agency;

        return establishment.length() >= 8
                ? establishment.substring(establishment.length() - 5)
                : establishment;
    }


    private void validateCommonRequest(CarteRequest request) {

        Objects.requireNonNull(request, "Carte request is required");

        if (request.getNumerocarte() == null
                || request.getNumerocarte().length() < 6) {
            throw new IllegalArgumentException(
                    "Card number must contain at least 6 characters"
            );
        }
    }


    private CarteResponse buildResponse(
            OpCarteEnAttente pending,
            Carte carte) {

        CarteResponse response = new CarteResponse();

        response.setIdoperation(pending.getIdoperation());
        response.setIdopcarte(carte.getIdopcarte());

        response.setNumerocarte(carte.getNumerocarte());
        response.setBanquetire(pending.getBanquetire());

        response.setMontantoperation(
                toDecimal(pending.getMontantoperation())
        );

        response.setOrigine(pending.getOrigine());
        response.setRio(pending.getRio());
        response.setReferenceinterne(pending.getReferenceinterne());

        response.setTypeoperation(pending.getTypeoperation());
        response.setEtat(pending.getEtat());

        return response;
    }


    private void saveCreationEvent(
            Integer operationId,
            String establishment,
            String name,
            String host) {

        try {

            evenementService.save(
                    evenementService.addEvenement_CRE(
                            operationId,
                            establishment,
                            name,
                            host,
                            "0",
                            String.valueOf(etatAttente)
                    )
            );

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Failed to create card operation event",
                    e
            );
        }
        
        
    }
    
    private Map<String, Map<String, String>> loadCarteLabels() {

        return libelleRepository.findByTypeIn(
                List.of("ORIGINE", "CODEOPERATION", "ETAT")
        ).stream()
         .filter(label -> label.getCode() != null)
         .collect(Collectors.groupingBy(
             Libelle::getType,
             Collectors.toMap(
                 Libelle::getCode,
                 Libelle::getLibelle1,
                 (first, second) -> first
             )
         ));
    }
}
