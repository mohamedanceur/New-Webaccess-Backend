package com.Atos.Webaccess.sib.agb.cheques;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class ChequeAGBServiceImpl implements ChequeAGBService {


	/*
	 @Autowired
	@Qualifier("sibJdbcTemplate")
	JdbcTemplate sibJdbcTemplate; 
	
	@Override
	public ChequeAGB getChequeAgb(String numeroCompte, String numeroCheque)
	{		
		
		try {
		String query = "SELECT * FROM ChequeScanAGB WHERE REFERENCE = ? and PART =?";
		
		List<ChequeAGB> cheques =sibJdbcTemplate.query(
		query,
		new Object[] {numeroCheque,numeroCompte.substring(0,7)}, 
		new ChequeAGBRowMapper()
		);
		
		if(cheques.size() > 0) {
			System.out.println("tailles des cheques retournés = " + cheques.size());
			return cheques.get(0);
		}
		else {
			System.out.println("Aucun cheques trouvé pour le numero de compte "+numeroCompte);	 
			ChequeAGB chequeAGB= new ChequeAGB();
			chequeAGB.setNumeroCheque("00000");
			return chequeAGB;
		}
		
		}catch(Exception e){
			System.out.println("Exception when trying to connect to AGB sib "+e);
			ChequeAGB chequeAGB= new ChequeAGB();
			chequeAGB.setNumeroCheque("00001");
			return chequeAGB;
		}
		

	}
			
	
	public class ChequeAGBRowMapper implements RowMapper<ChequeAGB> {
	    @Override
	    public ChequeAGB mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	
	    	ChequeAGB cheque = new ChequeAGB();
	 	    	
	        cheque.setNumeroCheque(rs.getString("REFERENCE"));
	        cheque.setNumeroCompte(rs.getString("PART"));
	        //compte.setTaux(rs.getString("TAUX"));
	 
	        return cheque;
	    }
	}
	*/
}
