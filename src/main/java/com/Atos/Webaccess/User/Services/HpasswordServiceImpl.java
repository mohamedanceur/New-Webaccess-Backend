package com.Atos.Webaccess.User.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Atos.Webaccess.User.Entities.Hpassword;
import com.Atos.Webaccess.User.Repositories.HpasswordRepository;

@Service
public class HpasswordServiceImpl implements HpasswordService {
	
	@Autowired
	private HpasswordRepository  hpasswordRepository;

	
	
	@Override
	public Hpassword saveHpassword(Hpassword h) {
		// TODO Auto-generated method stub
		return hpasswordRepository.save(h);
	}
	
	@Override
	public  List<Hpassword> getHpasswordByIdUser(long IdUser) {
		// TODO Auto-generated method stub
		return hpasswordRepository.findByIdutilisateur(IdUser);
	}
	
	
	

}
