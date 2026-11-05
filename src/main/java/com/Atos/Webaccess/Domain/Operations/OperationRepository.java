package com.Atos.Webaccess.Domain.Operations;

import com.Atos.Webaccess.Domain.Operations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    Optional<Operation> findByReferenceinterne(String referenceinterne);
}