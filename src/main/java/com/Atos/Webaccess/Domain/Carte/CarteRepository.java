package com.Atos.Webaccess.Domain.Carte;



import com.Atos.Webaccess.Domain.Carte.Carte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarteRepository extends JpaRepository<Carte, Integer> {

    Optional<Carte> findByIdoperationAndIdopcarte(
            Integer idoperation,
            Integer idopcarte
    );
}

