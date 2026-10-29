package com.Atos.Webaccess.sib.cpa.compte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Atos.Webaccess.sib.cpa.cheque.ChequeCPA;
import com.Atos.Webaccess.sib.cpa.cheque.ChequeCPAService;


@RestController
@RequestMapping("/sib/cpa")
public class CompteCPAController {
	/*
	@Autowired
	CompteCPAService compteCPAService;
	
	@PostMapping("/getcompte")
	public CompteCPA getCompte(@RequestBody CompteCPA compte)
	{
		
		return compteCPAService.getCompteCpa(compte.getNumerodecompte());
		
	}
	
	*/
	

}
