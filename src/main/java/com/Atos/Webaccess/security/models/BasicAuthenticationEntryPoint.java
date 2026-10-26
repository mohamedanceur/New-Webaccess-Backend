package com.Atos.Webaccess.security.models;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.Atos.Webaccess.Constants.AppConstants;
import com.google.gson.Gson;

public class BasicAuthenticationEntryPoint implements AuthenticationEntryPoint{

	private final Gson gson;
	
	public BasicAuthenticationEntryPoint(Gson gson) {
		this.gson = gson;
	}
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		Date time = new Date();
		System.out.println(time+" BasicAuthenticationEntryPoint");
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("Content-Type", "application/json");
		response.addHeader("Acces-control-Allow-Headers", "session");
		response.addHeader("Acces-control-Allow-Methods", "HEAD,GET,PUT,POST,DELETE,PATCH");
		response.addHeader("Acces-control-Allow-Origin", "*");
		
		response.getWriter().println(this.gson.toJson(new HashMap<String,String>(){{
			put(AppConstants.RESPONSE_MESSAGE_KEY,"Invalid Credentials");
		}}));
	}

}
