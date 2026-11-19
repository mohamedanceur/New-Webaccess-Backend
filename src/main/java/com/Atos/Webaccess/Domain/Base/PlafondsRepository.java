package com.Atos.Webaccess.Domain.Base;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlafondsRepository extends JpaRepository<Plafonds, String> {

    List<Plafonds> findAllByOrderByLimite1Asc();
}