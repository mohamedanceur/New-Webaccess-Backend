package com.Atos.Webaccess.Domain.Agence;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Integer> {

    boolean existsByCodeIgnoreCase(String code);

    Optional<Agence> findByCodeIgnoreCase(String code);

    List<Agence> findById_banqueOrderByCodeAsc(Integer idBanque);

    List<Agence> findByStatutOrderByCodeAsc(Integer statut);

    List<Agence> findDistinctByGrps_agence_IdOrderByCodeAsc(
        Integer groupeId
    );
}
