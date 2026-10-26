package com.Atos.Webaccess.Domain.Tache;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TacheService {

    private final TacheRepository tacheRepository;

    public List<Tache> getActiveTaches() {
        return tacheRepository.findByStatutOrderByLibelleAsc(1);
    }

    public Tache getTache(Integer id) {
        return tacheRepository.findById(id)
                .orElse(null);
    }

    @Transactional
    public Tache createTache(Tache tache) {
        tache.setId(null);
        tache.setStatut(1);

        return tacheRepository.save(tache);
    }

    @Transactional
    public boolean updateTache(Integer id, Tache updatedTache) {
        return tacheRepository.findById(id)
                .map(existingTache -> {
                    copyEditableFields(updatedTache, existingTache);
                    tacheRepository.save(existingTache);
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public boolean deleteTache(Integer id) {
        return tacheRepository.findById(id)
                .filter(tache -> Integer.valueOf(1).equals(tache.getStatut()))
                .map(tache -> {
                    tache.setStatut(0);
                    tacheRepository.save(tache);
                    return true;
                })
                .orElse(false);
    }

    private void copyEditableFields(Tache source, Tache target) {
        target.setType(source.getType());
        target.setTypeins(source.getTypeins());
        target.setNbmotifs(source.getNbmotifs());
        target.setLibelle(source.getLibelle());
        target.setEtatselect(source.getEtatselect());

        target.setEtatbascule1(source.getEtatbascule1());
        target.setEtatbascule2(source.getEtatbascule2());
        target.setEtatbascule3(source.getEtatbascule3());
        target.setEtatbascule4(source.getEtatbascule4());
        target.setEtatbascule5(source.getEtatbascule5());
        target.setEtatbascule6(source.getEtatbascule6());
        target.setEtatbascule7(source.getEtatbascule7());
        target.setEtatbascule8(source.getEtatbascule8());
        target.setEtatbascule9(source.getEtatbascule9());
        target.setEtatbascule10(source.getEtatbascule10());

        target.setVersion(source.getVersion());
        target.setDatemaj(source.getDatemaj());
    }
}
