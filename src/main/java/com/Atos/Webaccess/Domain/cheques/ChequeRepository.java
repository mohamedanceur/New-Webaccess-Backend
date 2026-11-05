
package com.Atos.Webaccess.Domain.cheques;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChequeRepository extends JpaRepository<Cheque, Integer> {

    List<Cheque> findByNumerocheque(String numerocheque);

}
