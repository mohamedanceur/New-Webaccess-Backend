
package com.Atos.Webaccess.Domain.SystemeBancaire;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemeBancaireRepository
        extends JpaRepository<SystemeBancaire, Integer> {
	 Optional<SystemeBancaire> findByIdsystemebancaire(Integer id);
	}

