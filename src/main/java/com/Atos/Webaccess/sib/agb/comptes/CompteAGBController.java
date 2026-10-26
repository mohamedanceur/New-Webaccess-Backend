package com.Atos.Webaccess.sib.agb.comptes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sib/agb")
public class CompteAGBController {
	
	@Autowired
	CompteAGBService compteAGBService;
	
	@PostMapping("/getcompte")
	public CompteAGB getCompte(@RequestBody CompteAGB compte)
	{
		return compteAGBService.getCompteAgb(compte.getNumeroCompte());
		
	}

}
