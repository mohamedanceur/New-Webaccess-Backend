package com.Atos.Webaccess.Domain.Base;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaysRepository extends JpaRepository<Pays, Integer> {

    List<Pays> findAllByOrderByIdAsc();
}