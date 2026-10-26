package com.Atos.Webaccess.Domain.Agence;



import com.Atos.Webaccess.Domain.Banque.Banque;
import com.Atos.Webaccess.Domain.Banque.BanqueRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgenceServiceImpl implements AgenceService {

    private final AgenceRepository agenceRepository;
    private final BanqueRepository banqueRepository;

    @Override
    public List<AgenceResponse> findAll() {
        return agenceRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public AgenceResponse findById(Integer id) {
        return toResponse(getAgenceOrThrow(id));
    }

    @Override
    @Transactional
    public AgenceResponse create(AgenceRequest request) {
        Banque banque = banqueRepository.findById(request.getId_banque())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Banque not found"));

        // Legacy rule: full agency code = bank code + supplied code.
        String fullCode = banque.getCode() + request.getCode();

        if (agenceRepository.existsByCodeIgnoreCase(fullCode)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Agency code already exists");
        }

        Agence agence = new Agence();
        agence.setCode(fullCode);
        agence.setCode_pc(request.getCode_pc());
        agence.setLibelle(request.getLibelle().toUpperCase(Locale.ROOT));
        agence.setId_banque(banque.getId());
        agence.setStatut(1);
        agence.setDatemaj(new Date());

        return toResponse(agenceRepository.save(agence));
    }

    @Override
    @Transactional
    public AgenceResponse update(Integer id, AgenceRequest request) {
        Agence agence = getAgenceOrThrow(id);

        // Legacy rule: an inactive agency cannot be updated.
        if (Integer.valueOf(0).equals(agence.getStatut())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Inactive agency cannot be updated");
        }

        // Legacy UpdateAgence did not change the bank or agency code.
        agence.setLibelle(request.getLibelle().toUpperCase(Locale.ROOT));
        agence.setCode_pc(request.getCode_pc());
        agence.setDatemaj(new Date());

        return toResponse(agenceRepository.save(agence));
    }

    @Override
    @Transactional
    public AgenceResponse changeStatus(Integer id, Integer statut) {
        if (statut == null || (statut != 0 && statut != 1)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Status must be 0 or 1");
        }

        Agence agence = getAgenceOrThrow(id);
        agence.setStatut(statut);
        agence.setDatemaj(new Date());

        return toResponse(agenceRepository.save(agence));
    }

    @Override
    public List<AgenceResponse> findByBank(Integer idBanque) {
        return agenceRepository.findById_banqueOrderByCodeAsc(idBanque)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<AgenceResponse> findByGroup(Integer idGroupe) {
        return agenceRepository
                .findDistinctByGrps_agence_IdOrderByCodeAsc(idGroupe)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<AgenceResponse> findAllValid() {
        return agenceRepository.findByStatutOrderByCodeAsc(1)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public String getAgenceName(Integer id) {
        return agenceRepository.findById(id)
                .map(Agence::getLibelle)
                .orElse("");
    }

    @Override
    public String getAgenceLibelle(String code) {
        return agenceRepository.findByCodeIgnoreCase(code)
                .map(Agence::getLibelle)
                .orElse("");
    }

    private Agence getAgenceOrThrow(Integer id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agency not found"));
    }

    private AgenceResponse toResponse(Agence agence) {
        String libelleBanque = agence.getBankAgence() == null
                ? null
                : agence.getBankAgence().getLibelle();

        return new AgenceResponse(
                agence.getId(),
                agence.getCode(),
                agence.getCode_pc(),
                agence.getLibelle(),
                agence.getStatut(),
                agence.getDatemaj(),
                agence.getId_banque(),
                libelleBanque
        );
    }
}
