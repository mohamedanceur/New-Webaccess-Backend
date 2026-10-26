package com.Atos.Webaccess.security.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Atos.Webaccess.security.entities.AuthToken;
import com.Atos.Webaccess.User.Entities.User;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {

	List<AuthToken> findByUser(User user);
	
}
