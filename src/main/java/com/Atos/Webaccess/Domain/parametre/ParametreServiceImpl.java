package com.Atos.Webaccess.Domain.parametre;

import static com.Atos.Webaccess.Constants.AppConstants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Atos.Webaccess.Commun.Commun;

@Service
public class ParametreServiceImpl implements ParametreService {

	@Autowired
	private final ParametreRepository parametreRepository;

	public ParametreServiceImpl(ParametreRepository parametreRepository) {

		this.parametreRepository = parametreRepository;
		this.initialize();

	}

	@Override
	public void initialize() {

		if (this.parametreRepository.count() > 0)
			return;

		Parametre parametre = new Parametre();
		Commun cmn = new Commun();
		parametre.setCodeBanque(cmn.readProp(DOMAIN_CODE_BANQUE));
		parametre.setiMIsDirectory("/ROOT_STC/IMI/");
		parametre.setBordereauDirectory("\\bordereaux");
		parametre.setLotsDirectory("/ROOT_STC/LOI/");
		parametre.setTemplateDirectory("templates/");
		parametre.setPosteUtilisateur("01");
		parametre.setWorkDirectory("C:\\TEMP");
		parametre.setHpassword(1);
		parametre.setpWvalidity(90);
		parametre.setTextToBePrinted(" ");
		parametre.setTextToBePrinted(cmn.readProp(DOMAIN_LIBELLE_BANQUE));

		this.parametreRepository.save(parametre);
		this.parametreRepository.flush();
	}

	@Override
	public Parametre getParametreByBanque(String banque) {
		return parametreRepository.findAll().get(0);
	}

	@Override
	public int updateParametre(Parametre parametre) {
		System.out.println(parametre.getWorkDirectory());
		Commun cmn = new Commun();
		parametre.setCodeBanque(cmn.readProp(DOMAIN_CODE_BANQUE));
		parametre.setPosteUtilisateur("01");
		if (parametre.getiMIsDirectory().trim().isEmpty())
			parametre.setiMIsDirectory("/ROOT_STC/IMI/");
		if (parametre.getBordereauDirectory().trim().isEmpty())
			parametre.setBordereauDirectory(System.getProperty("user.home") + "\\bordereaux");
		if (parametre.getLotsDirectory().trim().isEmpty())
			parametre.setLotsDirectory("/ROOT_STC/LOI/");
		if (parametre.getTemplateDirectory().trim().isEmpty())
			parametre.setTemplateDirectory("templates/");
		if (parametre.getWorkDirectory().trim().isEmpty())
			parametre.setWorkDirectory("C:\\TEMP");
		if (parametre.getHpassword() == 1 && parametre.getpWvalidity() == 0)
			parametre.setpWvalidity(90);
		parametre.setTextToBePrinted(" ");

		try {
			parametreRepository.saveAndFlush(parametre);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return RETURN_ERROR_DATABASE;
		}
		return RETURN_SUCCES ; 
	}

}
