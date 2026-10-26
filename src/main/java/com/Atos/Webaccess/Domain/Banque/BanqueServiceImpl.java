package com.Atos.Webaccess.Domain.Banque;



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
public class BanqueServiceImpl implements BanqueService {

    private final BanqueRepository banqueRepository;

    @Override
    public List<BanqueResponse> findAll() {
        return banqueRepository.findAllByOrderByLibelleAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public BanqueResponse findById(Integer id) {
        Banque banque = getRequiredBanque(id);

        // Legacy GetBanque returns algo as 0 when it is null.
        if (banque.getAlgo() == null) {
            banque.setAlgo(0);
        }

        return toResponse(banque);
    }

    @Override
    @Transactional
    public BanqueResponse create(BanqueRequest request) {
        if (banqueRepository.existsByCodeIgnoreCase(request.code())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A bank with this code already exists"
            );
        }

        Banque banque = new Banque();
        banque.setCode(request.code());
        banque.setIdPays(request.idPays());
        banque.setLibelle(request.libelle());
        banque.setCodeBin(request.codeBin());
        banque.setAlgo(request.algo());
        banque.setBanqueMere(request.banqueMere());
        banque.setStatut(1);

        Banque saved = banqueRepository.save(banque);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public BanqueResponse update(Integer id, BanqueRequest request) {
        Banque banque = getRequiredBanque(id);

        if (Integer.valueOf(0).equals(banque.getStatut())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "An inactive bank cannot be modified"
            );
        }

        /*
         * Preserve the legacy UpdateBanque behavior:
         * update label, country, date, algorithm and parent-bank flag.
         * Do not update code or codeBin here.
         */
        banque.setLibelle(request.libelle().toUpperCase(Locale.ROOT));
        banque.setIdPays(request.idPays());
        banque.setDatemaj(new Date());
        banque.setAlgo(request.algo() == null ? 0 : 1);
        banque.setBanqueMere(request.banqueMere());

        return toResponse(banqueRepository.save(banque));
    }

    @Override
    @Transactional
    public BanqueResponse changeStatus(Integer id, Integer statut) {
        if (statut == null || (statut != 0 && statut != 1)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status must be 0 or 1"
            );
        }

        Banque banque = getRequiredBanque(id);
        banque.setStatut(statut);
        banque.setDatemaj(new Date());

        return toResponse(banqueRepository.save(banque));
    }

    @Override
    public String getBanqueName(Integer id) {
        return banqueRepository.findById(id)
                .map(Banque::getLibelle)
                .orElse("");
    }

    @Override
    public List<BanqueResponse> findByGroupe(Integer idGroupe) {
        return banqueRepository.findByGroupeId(idGroupe)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public Integer getBanqueMereId() {
        return banqueRepository.findBanqueMereIds()
                .stream()
                .findFirst()
                .orElse(-1);
    }

    private Banque getRequiredBanque(Integer id) {
        return banqueRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bank not found: " + id
                ));
    }

    private BanqueResponse toResponse(Banque banque) {
        return new BanqueResponse(
                banque.getId(),
                banque.getCode(),
                banque.getIdPays(),
                banque.getLibelle(),
                banque.getCodeBin(),
                banque.getAlgo(),
                banque.getBanqueMere(),
                banque.getStatut(),
                banque.getDatemaj()
        );
    }
}
