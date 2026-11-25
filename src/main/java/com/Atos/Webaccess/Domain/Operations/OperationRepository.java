
package com.Atos.Webaccess.Domain.Operations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.Optional;

@Repository
public interface OperationRepository
        extends JpaRepository<Operation, Integer>,
                JpaSpecificationExecutor<Operation> {

    Optional<Operation> findByReferenceinterne(String referenceinterne);
    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Operation o set o.etat = :to , o.datetraitement = :now where o.idoperation = :id and o.etat = :from")
    int transition(@Param("id") Integer id,@Param("to") Integer to,@Param("now") Date now,@Param("from") Integer from);
    
    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Operation o set o.etat = :to , o.datetraitement = :now , o.motifrejet = :motif where o.idoperation = :id and o.etat = :from")
    int transitionWithReject(@Param("id") Integer id,@Param("to") Integer to,@Param("now") Date now,@Param("from") Integer from,@Param("motif") String motif);
    
 // OperationRepository
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Operation o set o.etat = :to, o.datetraitement = :now where o.idoperation in :ids and o.etat = :from")
    int transitionAll(@Param("ids") Collection<Integer> ids, @Param("to") Integer to,
                      @Param("now") Date now, @Param("from") Integer from);
}
