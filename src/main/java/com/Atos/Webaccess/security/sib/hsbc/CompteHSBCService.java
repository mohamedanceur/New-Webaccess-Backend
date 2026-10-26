package com.Atos.Webaccess.security.sib.hsbc;

import java.util.List;

public interface CompteHSBCService {
	
	List<CompteHSBC> getNomAdresseBeneficiaire(String numeroCompte);

}
