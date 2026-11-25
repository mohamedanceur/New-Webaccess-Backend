package com.Atos.Webaccess.Domain.Tache;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Integer> {

    /**
     * Returns active tasks ordered alphabetically by label.
     * Equivalent to: WHERE STATUT = 1 ORDER BY LIBELLE
     */
    List<Tache> findByStatutOrderByLibelleAsc(Integer statut);
    
    @Query("""
    		  select t from Tache t join t.usersTache u
    		  where u.id = :userId and t.statut = 1
    		    and (t.type = :sens or t.type = '*')
    		    and (t.typeins like :typeins or t.typeins = '*')
    		  order by t.etatselect""")
    		List<Tache> findForUser(Long userId, String sens, String typeins);
    
 // TacheRepository
    List<Tache> findByTypeAndStatutOrderByEtatselectAsc(String type, Integer statut);
}
