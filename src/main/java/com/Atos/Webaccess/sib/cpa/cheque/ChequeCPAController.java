package com.Atos.Webaccess.sib.cpa.cheque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Atos.Webaccess.sib.cpa.compte.CompteCPAService;

@RestController
@RequestMapping("/sib/cpa/cheque")
public class ChequeCPAController {
/*
	@Autowired
	ChequeCPAService chequeCPAService;

	@PostMapping("/getetatcheque")
	public ChequeCPA getCheque(@RequestBody ChequeCPA chequeCpa) {
		
		System.out.println("DEMANDE ETAT CHEQUE");
		
		return chequeCPAService.getEtatChequeCpa(chequeCpa.getNumeroCompte(), chequeCpa.getAgence(),
				chequeCpa.getBanque(), chequeCpa.getNumeroCheque());

	}
*/
}
