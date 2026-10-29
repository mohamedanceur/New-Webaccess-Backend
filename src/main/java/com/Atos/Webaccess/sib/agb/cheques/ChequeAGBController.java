package com.Atos.Webaccess.sib.agb.cheques;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sib/agb")
public class ChequeAGBController {
	
	@Autowired
	ChequeAGBService chequeAGBService;
	
	@PostMapping("/getCheque")
	public ChequeAGB getCompte(@RequestBody ChequeAGB cheque)
	{
		//return chequeAGBService.getChequeAgb(cheque.getNumeroCompte(), cheque.getNumeroCheque());
		return null;
	}

}
