package com.Atos.Webaccess.security.controllers;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.Atos.Webaccess.Constants.AppConstants.*;
@RestController
@RequestMapping(value="/", produces = "application/json")
public class SecurityController {
	
	@Autowired
	@Qualifier("springSecurityFilterChain")
	private Filter springSecurityFilterChain;
	
	@PostConstruct
	public void inPostConstruct() {
		FilterChainProxy filterChainProxy = (FilterChainProxy) springSecurityFilterChain;
		List<SecurityFilterChain> securityFilterChain = filterChainProxy.getFilterChains();
		securityFilterChain.stream()
									.flatMap(chain -> chain.getFilters().stream())
									.forEach(filter -> System.out.println(filter.getClass() + " "));
	}
	
	
	@GetMapping("/anon")
	@PreAuthorize("isAnonymous()")
	public Map<String, Object> anonAction(){
		return new HashMap<String,Object>() {{
			put(RESPONSE_MESSAGE_KEY,"You are anonymous");
		}};
	}
	
	
	@GetMapping("/logged")
	@PreAuthorize("isFullyAuthenticated()")
	public Map<String, Object> loggedAction(){
		return new HashMap<String,Object>() {{
			put(RESPONSE_MESSAGE_KEY,"You are logged in!");
		}};
	}
	
	
	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Map<String, Object> adminAction(){
		return new HashMap<String,Object>() {{
			put(RESPONSE_MESSAGE_KEY,"You are Admin!");
		}};
	}
	
	
	@PostMapping("/authorize")
	public Map<String, Object> authorize( HttpSession session ){
		
		/*Date time = new Date();
		System.out.println(time+" authorize");
		
		Object user_id = session.getAttribute(USER_ID);
		System.out.println("User: " +user_id);
		mainJdbcTemplate.queryForList("select password from users where id = "+user_id);*/
		
		return new HashMap<String,Object>() {{
			
			put(AUTH_TOKEN_NAME,session.getAttribute(AUTH_TOKEN_NAME)) ;
			put(FIRST_LOGIN,session.getAttribute(FIRST_LOGIN));
			put(USER_KEY,session.getAttribute(USER_KEY));
			put(USER_ID,session.getAttribute(USER_ID));
			//put(USER_ID,session.getAttribute(USER_ID));
			put(WRONG_STATUS,session.getAttribute(WRONG_STATUS));
			put(PW_EXPIRED,session.getAttribute(PW_EXPIRED));

		}};
	}
	
	
	
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public Map<String, Object> error(){
		return new HashMap<String,Object>() {{
			put(RESPONSE_MESSAGE_KEY,"You are not allowed to acces this page");
		}};
	}
	
	
	
}
