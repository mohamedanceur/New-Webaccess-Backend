package com.Atos.Webaccess.sib.cpa.cheque;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


@Service
public class ChequeCPAServiceImpl implements ChequeCPAService {
	/*
	@Autowired
	@Qualifier("sibJdbcTemplate")
	JdbcTemplate sibJdbcTemplate;

	
	@Override
	public ChequeCPA getEtatChequeCpa(String numeroCompte, String agence, String banque, String numeroCheque) {
		try {
			ChequeCPA chequeCPA = new ChequeCPA();
			String query = "select * from SIBICHQ WHERE banque = ? and guichet = ? and compte = ? and numchq = ? ";

			// Log de la requête
			System.out.println("Requête Cheque : SELECT * FROM SIBICHQ WHERE BANQUE = "+ banque+" AND GUICHET = "+agence+" AND COMPTE = "+numeroCompte+ " AND NUMCHQ = "+ numeroCheque);
			
			
			List<ChequeCPA> listOfCheques = sibJdbcTemplate.query(query, new Object[] { "00" + banque, agence, numeroCompte, numeroCheque }, new ChequeCpaRowMapper());

			if (listOfCheques.size() > 0) {
				return listOfCheques.get(0);
			} else {
				System.out.println("Aucun cheque trouvé pour le numero de cheque " + numeroCheque);
				chequeCPA.setEtatCheque("X");
				return chequeCPA;
			}

		} catch (Exception e) {
			System.out.println("Exception when trying to connect to CPA CHEQUE sib ");
			return null;
		}
	}
	
	public class ChequeCpaRowMapper implements RowMapper<ChequeCPA> {
		@Override
		public ChequeCPA mapRow(ResultSet rs, int rowNum) throws SQLException {

			ChequeCPA chequeCPA = new ChequeCPA();
			chequeCPA.setAgence(rs.getString("guichet"));
			chequeCPA.setBanque(rs.getString("banque"));
			chequeCPA.setCle(rs.getString("clecpt"));
			chequeCPA.setEtatCheque(rs.getString("etatchq"));
			chequeCPA.setNumeroCheque(rs.getString("numchq"));
			chequeCPA.setNumeroCompte(rs.getString("compte"));

			return chequeCPA;
		}
	}
*/
}
