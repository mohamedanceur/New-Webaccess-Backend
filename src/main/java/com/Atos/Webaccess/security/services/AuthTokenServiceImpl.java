package com.Atos.Webaccess.security.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Atos.Webaccess.Constants.AppConstants;
import com.Atos.Webaccess.security.entities.AuthToken;
import com.Atos.Webaccess.security.repositories.AuthTokenRepository;
import com.Atos.Webaccess.User.Entities.User;

@Service
public class AuthTokenServiceImpl implements AuthTokenService{

	private final AuthTokenRepository authTokenRepository;
	
	@Autowired
	public AuthTokenServiceImpl(AuthTokenRepository authTokenRepository) {
		this.authTokenRepository = authTokenRepository;
	}
	@Override
	public void update(AuthToken token) {
		
		token.setLastAccesTime(LocalDateTime.now());
		
		this.authTokenRepository.saveAndFlush(token);
	}

	@Override
	public void delete(AuthToken token) {
		
		this.authTokenRepository.delete(token);
	}

	@Override
	public boolean isTokenExpired(AuthToken token) {

	    if (token == null || token.getLastAccesTime() == null) {
	        return true;
	    }

	    LocalDateTime expirationTime = token.getLastAccesTime()
	        .plusMinutes(AppConstants.MAX_ALLOWED_INACTIVE_SESSION_MIN);

	    return !LocalDateTime.now().isBefore(expirationTime);
	}

	@Override
	public AuthToken create(User loggedInUser) {
		AuthToken token = new AuthToken();
		
		token.setUser(loggedInUser);
		token.setLastAccesTime(LocalDateTime.now());
		this.authTokenRepository.saveAndFlush(token);
		
		return token;
	}

	@Override
	public AuthToken findById(String id) {
		return this.authTokenRepository.findById(id).orElse(null);
	}

	@Override
	public List<AuthToken> findByUser(User user) {
		return this.authTokenRepository.findByUser(user);
	}

}
