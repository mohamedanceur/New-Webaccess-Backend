package com.Atos.Webaccess.Domain.effets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EffetRepository extends JpaRepository<Effet, Integer> {

}