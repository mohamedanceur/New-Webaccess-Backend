package com.Atos.Webaccess.Domain.Groupes;


import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.Atos.Webaccess.Domain.Agence.Agence;
import com.Atos.Webaccess.Domain.Agence.AgenceRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupeServiceImpl implements GroupeService {

    private final GroupeRepository groupeRepository;
    private final AgenceRepository agenceRepository;

    @Override
    public GroupeResponse findById(Integer id) {
        return toResponse(getRequiredGroupe(id));
    }

    @Override
    public List<GroupeResponse> findAllActive() {
        return groupeRepository.findByStatutOrderByLibelleAsc(1)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public GroupeResponse create(GroupeRequest request) {
        Groupe groupe = new Groupe();

        groupe.setLibelle(request.libelle());
        groupe.setLibelleAgence(request.libelleAgence());
        groupe.setStatut(1);

        groupe.setAgencesGrp(loadAgences(request.agenceIds()));

        return toResponse(groupeRepository.save(groupe));
    }

    @Override
    @Transactional
    public GroupeResponse update(Integer id, GroupeRequest request) {
        Groupe groupe = getRequiredGroupe(id);

        if (Integer.valueOf(0).equals(groupe.getStatut())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "An inactive group cannot be modified"
            );
        }

        groupe.setLibelle(request.libelle());
        groupe.setLibelleAgence(request.libelleAgence());
        groupe.setAgencesGrp(loadAgences(request.agenceIds()));

        return toResponse(groupeRepository.save(groupe));
    }

    @Override
    @Transactional
    public GroupeResponse changeStatus(Integer id, Integer statut) {
        if (statut == null || (statut != 0 && statut != 1)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status must be 0 or 1"
            );
        }

        Groupe groupe = getRequiredGroupe(id);
        groupe.setStatut(statut);

        return toResponse(groupeRepository.save(groupe));
    }

    private Groupe getRequiredGroupe(Integer id) {
        return groupeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Group not found: " + id
                ));
    }

    private Set<Agence> loadAgences(Set<Integer> agenceIds) {
        if (agenceIds == null || agenceIds.isEmpty()) {
            return new HashSet<>();
        }

        List<Agence> agences = agenceRepository.findAllById(agenceIds);

        if (agences.size() != agenceIds.size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "One or more agency IDs do not exist"
            );
        }

        return new HashSet<>(agences);
    }

    private GroupeResponse toResponse(Groupe groupe) {
        Set<Integer> agenceIds = groupe.getAgencesGrp()
                .stream()
                .map(Agence::getId)
                .collect(Collectors.toSet());

        return new GroupeResponse(
                groupe.getId(),
                groupe.getLibelle(),
                groupe.getLibelleAgence() == null
                        ? " "
                        : groupe.getLibelleAgence(),
                groupe.getStatut() == null ? 0 : groupe.getStatut(),
                agenceIds
        );
    }
}
