package com.Atos.Webaccess.Domain.parametre;




public interface ParametreService {
			
			public  Parametre getParametreByBanque(String banque);
			
			void initialize();

			public int updateParametre(Parametre parametre);	
}
