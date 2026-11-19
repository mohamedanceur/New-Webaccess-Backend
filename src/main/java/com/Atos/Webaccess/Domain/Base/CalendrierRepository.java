package com.Atos.Webaccess.Domain.Base;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface CalendrierRepository
        extends JpaRepository<Calendrier, Integer> {

    List<Calendrier> findByTypedateIn(List<String> types);

    List<Calendrier> findByTypedateAndDatecalendrierGreaterThanEqual(
            String type,
            Date date
    );
}