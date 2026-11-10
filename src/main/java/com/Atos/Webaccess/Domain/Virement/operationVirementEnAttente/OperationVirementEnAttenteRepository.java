package com.Atos.Webaccess.Domain.Virement.operationVirementEnAttente;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OperationVirementEnAttenteRepository
        extends JpaRepository<OperationVirementEnAttente, Integer> {

    List<OperationVirementEnAttente> findByTypeoperationLike(String pattern);
}