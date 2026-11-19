
package com.Atos.Webaccess.Domain.Operations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationRepository
        extends JpaRepository<Operation, Integer>,
                JpaSpecificationExecutor<Operation> {

    Optional<Operation> findByReferenceinterne(String referenceinterne);
}
