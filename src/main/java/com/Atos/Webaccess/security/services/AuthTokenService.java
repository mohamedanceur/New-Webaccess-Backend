package com.Atos.Webaccess.security.services;

import java.util.List;

import com.Atos.Webaccess.security.entities.AuthToken;
import com.Atos.Webaccess.User.Entities.User;

public interface AuthTokenService {

	void update(AuthToken token);
	
	void delete(AuthToken token);
	
	boolean isTokenExpired(AuthToken token);
	
	AuthToken create(User loggedInUser);
	
	AuthToken findById(String id);
	
	List<AuthToken> findByUser(User user);
	
	
	
}
