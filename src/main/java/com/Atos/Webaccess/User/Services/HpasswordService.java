package com.Atos.Webaccess.User.Services;

import java.util.List;

import com.Atos.Webaccess.User.Entities.Hpassword;

public interface HpasswordService {
	
 public Hpassword saveHpassword(Hpassword h );
 public List<Hpassword> getHpasswordByIdUser(long IdUser);
 
}
