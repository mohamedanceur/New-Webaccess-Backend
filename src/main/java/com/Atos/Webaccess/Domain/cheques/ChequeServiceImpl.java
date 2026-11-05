package com.Atos.Webaccess.Domain.cheques;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChequeServiceImpl implements ChequeService {

    private final ChequeRepository chequeRepository;

    public ChequeServiceImpl(ChequeRepository chequeRepository) {
        this.chequeRepository = chequeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cheque> findAll() {
        return chequeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.Optional<Cheque> findById(Integer id) {
        return chequeRepository.findById(id);
    }

    @Override
    public Cheque save(Cheque cheque) {
        return chequeRepository.save(cheque);
    }

    @Override
    public Cheque update(Integer id, Cheque chequeDetails) {

        Cheque existingCheque = chequeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Cheque not found with ID: " + id));

        existingCheque.setEtatcheque(chequeDetails.getEtatcheque());
        existingCheque.setRibtire(chequeDetails.getRibtire());
        existingCheque.setNomOrdinateur(chequeDetails.getNomOrdinateur());
        existingCheque.setNumerocheque(chequeDetails.getNumerocheque());
        existingCheque.setAgenceremettant(chequeDetails.getAgenceremettant());
        existingCheque.setFichierimage(chequeDetails.getFichierimage());
        existingCheque.setIdimageuap(chequeDetails.getIdimageuap());
        existingCheque.setDevise(chequeDetails.getDevise());
        existingCheque.setNomUtilisateur(chequeDetails.getNomUtilisateur());
        existingCheque.setNumcomptetire(chequeDetails.getNumcomptetire());
        existingCheque.setBanqueremettant(chequeDetails.getBanqueremettant());
        existingCheque.setIdfichierimgin(chequeDetails.getIdfichierimgin());
        existingCheque.setMontant(chequeDetails.getMontant());
        existingCheque.setBanquetire(chequeDetails.getBanquetire());
        existingCheque.setArchive(chequeDetails.getArchive());
        existingCheque.setTypeDeCheque(chequeDetails.getTypeDeCheque());
        existingCheque.setEtablissement(chequeDetails.getEtablissement());
        existingCheque.setDateemission(chequeDetails.getDateemission());
        existingCheque.setAgencetire(chequeDetails.getAgencetire());
        existingCheque.setCorrectionpisteocrcmc7(
                chequeDetails.getCorrectionpisteocrcmc7());
        existingCheque.setAdressetire(chequeDetails.getAdressetire());
        existingCheque.setNomtire(chequeDetails.getNomtire());
        existingCheque.setPathimage(chequeDetails.getPathimage());

        return chequeRepository.save(existingCheque);
    }

    @Override
    public void deleteById(Integer id) {
        if (!chequeRepository.existsById(id)) {
            throw new RuntimeException("Cheque not found with ID: " + id);
        }

        chequeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cheque> findByNumeroCheque(String numeroCheque) {
        return chequeRepository.findByNumerocheque(numeroCheque);
    }
}