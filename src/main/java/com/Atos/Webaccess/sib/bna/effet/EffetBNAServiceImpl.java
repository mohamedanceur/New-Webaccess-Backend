package com.Atos.Webaccess.sib.bna.effet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/*import com.atos.security.domain.agence.Agence;
import com.atos.security.domain.cheques.Cheque;
import com.atos.security.domain.cheques.RechercheChequeRequest;
import com.atos.security.domain.cheques.ChequeServiceImpl.ChequeRowMapper;
import com.atos.security.domain.cheques.ChequeServiceImpl.LotRowMapper;
import com.atos.security.domain.cheques.ChequeServiceImpl.RemiseRowMapper;
import com.atos.security.domain.effets.Effet;
import com.atos.security.domain.effets.EffetServiceImpl.EffetRowMapper;
import com.atos.security.domain.groupe.Groupe;
import com.atos.security.domain.groupe.GroupeRepository;
import com.atos.security.domain.lot.Lot;
import com.atos.security.domain.remise.Remise;
import com.atos.security.domain.remise.RemiseRepository;
import com.atos.security.sib.bna.effet.EffetBNA;
import com.atos.security.users.entities.User;
import com.atos.security.users.services.UserService;
import com.atos.tesseract.main.OcrReader;*/

@Service
public class EffetBNAServiceImpl implements EffetBNAService {
	@Autowired
	@Qualifier("sibJdbcTemplate")
	JdbcTemplate sibJdbcTemplate;
	
	/*@Autowired
	@Qualifier("mainJdbcTemplate")
	JdbcTemplate mainJdbcTemplate;*/
	
	
	public class BkfrRowMapper implements RowMapper<EffetBNA> {
		@Override
		public EffetBNA mapRow(ResultSet rs, int rowNum) throws SQLException {

			EffetBNA effetBNA = new EffetBNA();
			effetBNA.setSor(rs.getString("SOR"));
			return effetBNA;
		}
	}
	

	@Override
	public List<EffetBNA> list_rech_op_bkfr(String where_clause) {
		// TODO Auto-generated method stub
		String req = "select b.SOR from BKEFR as b where b.NEFF in "+ where_clause;
		System.out.println(req);
		List<EffetBNA> listOfEffets = sibJdbcTemplate.query(req, new BkfrRowMapper());
		//List<EffetBNA> listOfEffets = mainJdbcTemplate.query(req, new BkfrRowMapper());
		return listOfEffets;

	}


	



}
