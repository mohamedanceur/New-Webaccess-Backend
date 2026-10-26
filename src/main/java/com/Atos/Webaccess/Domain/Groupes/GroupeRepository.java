package com.Atos.Webaccess.Domain.Groupes;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupeRepository extends JpaRepository<Groupe, Integer> {

    List<Groupe> findByStatutOrderByLibelleAsc(Integer statut);

    List<Groupe> findAllByOrderByLibelleAsc();

    int countByAgencesGrp_Id(Integer agenceId);
}

