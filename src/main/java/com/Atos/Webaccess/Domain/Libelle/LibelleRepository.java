
package com.Atos.Webaccess.Domain.Libelle;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibelleRepository
        extends JpaRepository<Libelle, Integer> {

    List<Libelle> findByTypeIn(Collection<String> types);
    
    List<Libelle> findByTypeOrderByCodeAsc(String type);

    List<Libelle> findByTypeAndLibelle3(
            String type,
            String libelle3
    );
}
