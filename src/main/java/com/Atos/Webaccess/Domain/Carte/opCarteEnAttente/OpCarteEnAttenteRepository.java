package com.Atos.Webaccess.Domain.Carte.opCarteEnAttente;

import com.Atos.Webaccess.Domain.Carte.Carte;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpCarteEnAttenteRepository
        extends JpaRepository<OpCarteEnAttente, Integer> {

    List<OpCarteEnAttente> findByIdinstrument(Integer idinstrument);

    @Query("""
        SELECT o.idoperation,
               c.numerocarte,
               o.banquetire,
               o.montantoperation,
               o.origine,
               o.rio,
               o.referenceinterne,
               o.typeoperation,
               o.etat,
               c.idopcarte
        FROM OpCarteEnAttente o, Carte c
        WHERE o.idinstrument = c.idopcarte
          AND (o.typeoperation LIKE :withdrawalPattern
               OR o.typeoperation LIKE :paymentPattern)
        ORDER BY c.numerocarte
        """)
    List<Object[]> findPendingCardOperations(
            @Param("withdrawalPattern") String withdrawalPattern,
            @Param("paymentPattern") String paymentPattern
    );
}