package com.Atos.Webaccess.Domain.parametre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ParametreRepository extends JpaRepository <Parametre, Long>{

//public Parametre findByCodeBanque(String codeBanque); 
	
	
}
