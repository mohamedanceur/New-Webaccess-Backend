package com.Atos.Webaccess.Domain.cheques;

import java.util.List;
import java.util.Optional;

public interface ChequeService {

    List<Cheque> findAll();

    Optional<Cheque> findById(Integer id);

    Cheque save(Cheque cheque);

    Cheque update(Integer id, Cheque cheque);

    void deleteById(Integer id);

    List<Cheque> findByNumeroCheque(String numeroCheque);
}
