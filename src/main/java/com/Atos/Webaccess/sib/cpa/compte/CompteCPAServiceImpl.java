package com.Atos.Webaccess.sib.cpa.compte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class CompteCPAServiceImpl implements CompteCPAService {

	@Autowired
	@Qualifier("sibJdbcTemplate")
	JdbcTemplate sibJdbcTemplate;

	@Override
	public CompteCPA getCompteCpa(String numeroCompte) {

		try {
			String query = "select * from SIBICCPT WHERE compte = ? and banque = ? and guichet = ? ";

			System.out.println("Requête = SELECT * FROM SIBICCPT WHERE compte = " + numeroCompte.substring(8, 18)
					+ " and banque = " + numeroCompte.substring(0, 3) + " and guichet = "
					+ numeroCompte.substring(3, 8)); // Log de la requête
			
			System.out.println("Tentative de connexion au SIB ");
			String hostAddress = sibJdbcTemplate.getDataSource().getConnection().getMetaData().getURL();
            System.out.println("Connected to: " + hostAddress);
			List<CompteCPA> comptes = sibJdbcTemplate.query(query, new Object[] { numeroCompte.substring(8, 18),
					"00" + numeroCompte.substring(0, 3), numeroCompte.substring(3, 8) }, new CompteCpaRowMapper());
			
			
			System.out.println("Connexion avec le SIB effectuée avec succès");
			if (comptes.size() > 0) {
				return comptes.get(0);
			} else {
				System.out.println("Aucun compte trouvé pour le numero de compte " + numeroCompte);
				CompteCPA compte= new CompteCPA();
 				compte.setNumerodecompte("-1");
 				return compte;
			}

		} catch (Exception e) {
			System.out.println("Exception when trying to connect to CPA sib ");
			CompteCPA compte= new CompteCPA();
			compte.setNumerodecompte("-2");
			return compte;
		}

	}

	public class CompteCpaRowMapper implements RowMapper<CompteCPA> {
		@Override
		public CompteCPA mapRow(ResultSet rs, int rowNum) throws SQLException {

			CompteCPA compte = new CompteCPA();
			
			compte.setAgence(rs.getString("banque").substring(2,5) + rs.getString("guichet"));
			compte.setNumerodecompte(rs.getString("compte"));
			compte.setCle(rs.getString("clecpt"));

			compte.setNom(rs.getString("nomclirais") + " " + rs.getString("prenomcli"));
			compte.setAdresse(rs.getString("adr1cli") + " " + rs.getString("adr2cli") + " " + rs.getString("adr3cli"));
			compte.setLibReduitOppCompte(rs.getString("libroppct"));
			compte.setLibDetailOppCompte(rs.getString("libdoppct"));
			compte.setInterditChequier(rs.getString("interdchq"));
			compte.setCloture(rs.getString("oppblqct"));

			System.out.println("Récupération des informations du compte à partir du SIB :");
			System.out.println(compte);

			return compte;
		}
	}

	

}
