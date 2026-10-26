package com.Atos.Webaccess.security.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Base64;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Atos.Webaccess.Constants.AppConstants;
import com.Atos.Webaccess.security.entities.AuthToken;
import com.Atos.Webaccess.security.services.AuthTokenService;
import com.Atos.Webaccess.User.Entities.User;
import com.Atos.Webaccess.User.Services.UserService;
import com.Atos.Webaccess.User.Services.UserServiceImpl;

public class TokenAuthenticationFilter implements Filter {

	private final AuthTokenService authTokenService;
	final UserService userservice;
	final BCryptPasswordEncoder passwordEncoder;
	
	public TokenAuthenticationFilter(AuthTokenService authTokenService, UserService userservice, BCryptPasswordEncoder passwordEncoder) {
		this.authTokenService = authTokenService;
		this.userservice = userservice;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		Date time = new Date();
		
		HttpServletRequest httpServletRequest =  (HttpServletRequest) request;
		final String token = httpServletRequest.getHeader(AppConstants.AUTH_TOKEN_NAME);
		final String authorization = httpServletRequest.getHeader(AppConstants.AUTHORIZATION);
		final Enumeration<String> headers = httpServletRequest.getHeaders(AppConstants.AUTHORIZATION);
		//String auth = authorization.substring(6);
		//System.out.println(" auth : "+headers);
		while (headers.hasMoreElements()) {
            //System.out.println("Value is: " + headers.nextElement().substring(6));
			String decodedString = new String(
				    Base64.getDecoder().decode(headers.nextElement().substring(6)),
				    StandardCharsets.UTF_8
				);    		String[] values = decodedString.split(":", 2);
    		String credentialsUsername=values[0];
    		String credentialsPw = values[1];
    		System.out.println(" authentification for user "+credentialsUsername);
    		/*System.out.println(" wrong username and password: "+credentialsUsername+" "+credentialsPw);*/
    		
    		String password = userservice.getPasswordByUsername(credentialsUsername);
			HttpServletResponse httpResponse = (HttpServletResponse) response;
    		if (!passwordEncoder.matches(credentialsPw, password)) {
				httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    			httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				httpResponse.setContentType("application/json");
				httpResponse.getWriter().write("{\"message\":\":: :: L'authentification a échoué Identifiants invalides !\"}");
				httpResponse.getWriter().flush();
				return;
			}
    		System.out.println("CHECK IF RETURN WORKS");
    }
		
				
		/*String decodedString = new String(Base64.decodeBase64(auth));
		String[] values = decodedString.split(":", 2);
		String credentialsUsername=values[0];
		String credentialsPw = values[1];
		System.out.println(" wrong username and password: "+credentialsUsername+" "+credentialsPw);
		List<String> wrong_password = mainJdbcTemplate.queryForList("select password from users where username = "+credentialsUsername, String.class);
		if (!wrong_password.isEmpty()) 
		{
			System.out.println(passwordEncoder.matches(credentialsPw, wrong_password.get(0)));
		}*/
		
	   
		if(token != null) {
			
			AuthToken authToken = this.authTokenService.findById(token);
			
			if(authToken != null) {
				if(this.authTokenService.isTokenExpired(authToken)) {
				
					this.authTokenService.delete(authToken);
				}else {
				
					final User principal = authToken.getUser();
					final Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authentication);
					this.authTokenService.update(authToken);
					
				}
			}
		}
		
		System.out.println("=============================");
		System.out.println("response : ");
		System.out.println(response);
		System.out.println("request : ");
		System.out.println(request);
		System.out.println("=============================");
		chain.doFilter(request, response);
		
	}
}
