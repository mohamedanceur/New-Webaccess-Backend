package com.Atos.Webaccess.security.sib.hsbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.Atos.Webaccess.sib.agb.comptes.CompteAGB;
//import com.Atos.Webaccess.sib.agb.comptes.CompteAGBServiceImpl.CompteAgbRowMapper;

@Service
public class CompteHSBCServiceImpl implements CompteHSBCService {

	/* @Autowired
	RemiseRepository remiseRepository; */

	@Autowired
	@Qualifier("mainJdbcTemplate")
	JdbcTemplate mainJdbcTemplate;

	/*
	 * @Override public CompteHSBC getCompteHSBC(String numeroCompte) {
	 * 
	 * System.out.println("Numero de compte = " + numeroCompte); String
	 * nomBeneficiaire = remiseRepository.getNomBeneficiaire(numeroCompte).get(0);
	 * System.out.println("Nom beneficiaire === "+nomBeneficiaire); String
	 * adresseBeneficiaire =
	 * remiseRepository.getAdresseBeneficiaire(numeroCompte).get(0); CompteHSBC
	 * compteHsbc = new CompteHSBC(); compteHsbc.setNom(nomBeneficiaire);
	 * compteHsbc.setAdresse(adresseBeneficiaire);
	 * compteHsbc.setNumeroCompte(numeroCompte);
	 * System.out.println("RETURN NOM BENEFIRICIAIRE :: "+compteHsbc.getNom());
	 * return compteHsbc; }
	 */
	
	
	public List<CompteHSBC> getNomAdresseBeneficiaire(String numeroCompte) {

		String query = "SELECT * FROM BENEFICIAIRE WHERE 1=1 ";

		query += "AND RIB = " + numeroCompte.substring(3, 15);

		List<CompteHSBC> listOfNomAdresseBeneficiaire = mainJdbcTemplate.query(query, new CompteHsbcRowMapper());

		if (listOfNomAdresseBeneficiaire != null)
			return listOfNomAdresseBeneficiaire;
		else
			return null;
	}

	public class CompteHsbcRowMapper implements RowMapper<CompteHSBC> {
		@Override
		public CompteHSBC mapRow(ResultSet rs, int rowNum) throws SQLException {

			CompteHSBC compte = new CompteHSBC();
			
			compte.setNumeroCompte(rs.getString("RIB"));
			compte.setNom(rs.getString("NOM"));
			compte.setAdresse(rs.getString("ADRESSE"));

			return compte;
		}
	}
}
