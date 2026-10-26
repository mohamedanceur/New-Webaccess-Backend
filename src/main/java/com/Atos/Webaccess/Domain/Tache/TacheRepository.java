package com.Atos.Webaccess.Domain.Tache;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Integer> {

    /**
     * Returns active tasks ordered alphabetically by label.
     * Equivalent to: WHERE STATUT = 1 ORDER BY LIBELLE
     */
    List<Tache> findByStatutOrderByLibelleAsc(Integer statut);

}
