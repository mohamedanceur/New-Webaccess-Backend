
package com.Atos.Webaccess.Domain.Agence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Integer> {

    boolean existsByCodeIgnoreCase(String code);

    Optional<Agence> findByCodeIgnoreCase(String code);

    @Query("""
        SELECT a
        FROM Agence a
        WHERE a.id_banque = :idBanque
        ORDER BY a.code ASC
        """)
    List<Agence> findAgencesByBanqueId(
        @Param("idBanque") Integer idBanque
    );

    List<Agence> findByStatutOrderByCodeAsc(Integer statut);

    @Query("""
        SELECT DISTINCT a
        FROM Agence a
        JOIN a.grps_agence g
        WHERE g.id = :groupeId
        ORDER BY a.code ASC
        """)
    List<Agence> findAgencesByGroupeId(
        @Param("groupeId") Integer groupeId
    );
}
