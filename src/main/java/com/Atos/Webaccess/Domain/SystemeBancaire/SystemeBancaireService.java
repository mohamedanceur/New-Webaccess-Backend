
package com.Atos.Webaccess.Domain.SystemeBancaire;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SystemeBancaireService {

    private final SystemeBancaireRepository repository;

    @Transactional(readOnly = true)
    public SystemeBancaireConfig getConfiguration() {

        SystemeBancaire entity = repository.findById(1)
                .orElse(null);

        SystemeBancaireConfig config = new SystemeBancaireConfig();

        int tailleBanque = getValue(
                entity != null ? entity.getTaillecodebanque() : null, 3);

        int tailleAgence = getValue(
                entity != null ? entity.getTaillecodeagence() : null, 5);

        config.setTaillecodebanque(tailleBanque);
        config.setTaillecodeagence(tailleAgence);
        config.setTailleFinaleCodeAgence(tailleBanque + tailleAgence);

        config.setTaillenumerocompte(getValue(
                entity != null ? entity.getTaillenumerocompte() : null, 10));

        config.setTailleclerib(getValue(
                entity != null ? entity.getTailleclerib() : null, 2));

        config.setCodedevise(
                entity != null && entity.getCodedevise() != null
                        ? entity.getCodedevise()
                        : "DZD"
        );

        config.setTaillemontant(getValue(
                entity != null ? entity.getTaillemontant() : null, 15));

        config.setTaillenumerocheque(getValue(
                entity != null ? entity.getTaillenumerocheque() : null, 15));

        config.setDelaireglement(getValue(
                entity != null ? entity.getDelaireglement() : null, 1));

        config.setPoscodebanquerib(getValue(
                entity != null ? entity.getPoscodebanquerib() : null, 0));

        config.setPoscodeagencerib(getValue(
                entity != null ? entity.getPoscodeagencerib() : null, 3));

        config.setPosnumerocompterib(getValue(
                entity != null ? entity.getPosnumerocompterib() : null, 8));

        config.setPoscleribrib(getValue(
                entity != null ? entity.getPoscleribrib() : null, 18));

        config.setTaillerib(getValue(
                entity != null ? entity.getTaillerib() : null, 20));

        config.setTaillerio(getValue(
                entity != null ? entity.getTaillerio() : null, 38));

        config.setTailleiio(getValue(
                entity != null ? entity.getTailleiio() : null, 42));

        config.setTypenumerocompte(
                entity != null && entity.getTypenumerocompte() != null
                        ? entity.getTypenumerocompte()
                        : "A"
        );

        return config;
    }

    private Integer getValue(java.math.BigDecimal value, int defaultValue) {
        return value != null ? value.intValueExact() : defaultValue;
    }
}
