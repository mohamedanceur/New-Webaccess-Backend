package com.Atos.Webaccess.security.sib.hsbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sib/hsbc")
public class CompteHSBCController {

	@Autowired
	CompteHSBCService compteHSBCService;

	@PostMapping("/getcompte")
	public List<CompteHSBC> getCompte(@RequestBody CompteHSBC compte) {

		//List<CompteHSBC> listCompteHsbc = compteHSBCService.getNomAdresseBeneficiaire(compte.getNumeroCompte());
		
		return compteHSBCService.getNomAdresseBeneficiaire(compte.getNumeroCompte());

	}

}
