package com.Atos.Webaccess.sib.agb.comptes;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class CompteAGBServiceImpl implements CompteAGBService {


	
	@Autowired
	@Qualifier("sibJdbcTemplate")
	JdbcTemplate sibJdbcTemplate;
	
	@Override
	public CompteAGB getCompteAgb(String numeroCompte)
	{		
		
		try {
		String query = "SELECT * FROM BLOQUE WHERE RETAIL = ? ";
		
		List<CompteAGB> comptes =sibJdbcTemplate.query(
		query,
		new Object[] {numeroCompte}, 
		new CompteAgbRowMapper()
		);
		
		if(comptes.size() > 0) {
			System.out.println("tailles des comptes retournés = " + comptes.size());
			return comptes.get(0);
		}
		else {
			System.out.println("Aucun compte trouvé pour le numero de compte "+numeroCompte);	 
	        return null;
		}
		
		}catch(Exception e){
			System.out.println("Exception when trying to connect to AGB sib ");
	        return null;
		}
		

	}
			
	
	public class CompteAgbRowMapper implements RowMapper<CompteAGB> {
	    @Override
	    public CompteAGB mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	
	    	CompteAGB compte = new CompteAGB();
	 	    	
	        compte.setNumeroCompte(rs.getString("RETAIL"));
	        compte.setNom(rs.getString("NOM"));
	        compte.setPrenom("");
	        compte.setAdresse(rs.getString("ADR"));
	        compte.setCloture(rs.getString("CLOTURE"));
	        compte.setEnOpposition(rs.getString("OPPOS"));
	        //compte.setTaux(rs.getString("TAUX"));
	 
	        return compte;
	    }
	}
	
}
