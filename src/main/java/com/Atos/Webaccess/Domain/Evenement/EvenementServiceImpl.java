package com.Atos.Webaccess.Domain.Evenement;



import com.Atos.Webaccess.config.ApplicationParameters;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EvenementServiceImpl implements EvenementService {

    private final EvenementRepository evenementRepository;
    private final ApplicationParameters applicationParameters;

    public EvenementServiceImpl(
            EvenementRepository evenementRepository,
            ApplicationParameters applicationParameters) {

        this.evenementRepository = evenementRepository;
        this.applicationParameters = applicationParameters;
    }

    @Override
    @Transactional
    public Evenement save(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    @Override
    public Optional<Evenement> findById(Integer id) {
        return evenementRepository.findById(id);
    }

    /**
     * Prepares a CRE event.
     * Preserves the legacy addEvenement_CRE behavior.
     */
    @Override
    public Evenement addEvenement_CRE(
            Integer idop,
            String etablis,
            String name,
            String host,
            String etatInitial,
            String etatFinal
    ) throws Exception {

        Evenement event = new Evenement();

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyyMMdd");

        String currentDate = dateFormat.format(new Date());

        event.setEtatEven(0);
        event.setIdOper(idop);
        event.setApplic("Mwa");
        event.setCodBanq(etablis);
        event.setCodAgence(etablis + "00000");
        event.setPoste("00");
        event.setTypEven("CRE");
        event.setNomUser(name);
        event.setNomOrdi(host);
        event.setEtatI(Integer.parseInt(etatInitial));
        event.setEtatF(Integer.parseInt(etatFinal));
        event.setDateEven(dateFormat.parse(currentDate));

        return event;
    }

    /**
     * Prepares an INT approval event.
     * Preserves the legacy addEvenement_INT_APPROVE behavior.
     */
    @Override
    public Evenement addEvenement_INT_APPROVE(
            Integer idop,
            String name,
            String host,
            int etatInitial,
            int etatFinal,
            String idUser,
            int taillecodebanque,
            int taillecodeagence,
            String codeAgence
    ) throws Exception {

        Evenement event = new Evenement();

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentDate = dateFormat.format(new Date());

        event.setIdOper(idop);
        event.setEtatEven(0);
        event.setApplic("App");
        event.setCodBanq(applicationParameters.getCodeBanque());
        event.setCodAgence(codeAgence);
        event.setPoste("00");
        event.setTypEven("INT");
        event.setNomUser(name);
        event.setNomOrdi(host);
        event.setEtatI(etatInitial);
        event.setEtatF(etatFinal);
        event.setDateEven(dateFormat.parse(currentDate));
        event.setIdFichier(0);
        event.setIdUser(idUser);

        return event;
    }

    /**
     * Prepares an INT update event.
     * Preserves the legacy addEvenement_upadate behavior.
     */
    @Override
    public Evenement addEvenement_upadate(
            Integer idop,
            String name,
            String host,
            int etatInitial,
            int etatFinal,
            String idUser,
            int taillecodebanque,
            int taillecodeagence,
            String codeAgence
    ) throws Exception {

        Evenement event = new Evenement();

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentDate = dateFormat.format(new Date());

        event.setIdOper(idop);
        event.setEtatEven(0);
        event.setApplic("MWa");
        event.setCodBanq(applicationParameters.getCodeBanque());
        event.setCodAgence(codeAgence);
        event.setPoste("00");
        event.setTypEven("INT");
        event.setNomUser(name);
        event.setNomOrdi(host);
        event.setEtatI(etatInitial);
        event.setEtatF(etatFinal);
        event.setDateEven(dateFormat.parse(currentDate));
        event.setIdFichier(0);
        event.setIdUser(idUser);

        return event;
    }
}