package com.Atos.Webaccess.Domain.Banque;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BanqueRepository extends JpaRepository<Banque, Integer> {

    boolean existsByCodeIgnoreCase(String code);

    List<Banque> findAllByOrderByLibelleAsc();

    @Query("""
        select coalesce(b.algo, 0)
        from Banque b
        where b.id = :id
        """)
    Optional<Integer> findAlgoOrDefault(@Param("id") Integer id);

    @Query("""
        select distinct b
        from Banque b
        join b.agencesBanque a
        join a.grps_agence g
        where g.id = :groupeId
        order by b.id
        """)
    List<Banque> findByGroupeId(@Param("groupeId") Integer groupeId);

    @Query("""
        select b.id
        from Banque b
        where b.banqueMere = 1
        """)
    List<Integer> findBanqueMereIds();
    
    List<Banque> findByStatutOrderByLibelleAsc(Integer statut);
}
