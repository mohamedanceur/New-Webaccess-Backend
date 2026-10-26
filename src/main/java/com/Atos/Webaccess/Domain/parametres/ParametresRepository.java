package com.Atos.Webaccess.Domain.parametres;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ParametresRepository
        extends JpaRepository<Parametres, Integer> {

    @Query("""
        SELECT p.valeur
        FROM Parametres p
        WHERE p.parametre = :parametre
        """)
    List<String> findValeursByParametre(
            @Param("parametre") String parametre);
}
