package com.Atos.Webaccess.Domain.Evenement;


import com.Atos.Webaccess.Domain.Evenement.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Integer> {
}