package com.Atos.Webaccess.security.filters;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import static com.Atos.Webaccess.Constants.AppConstants.*;
import com.Atos.Webaccess.Commun.Commun;
import com.Atos.Webaccess.Domain.parametre.Parametre;
import com.Atos.Webaccess.Domain.parametre.ParametreService;
import com.Atos.Webaccess.security.services.AuthTokenService;
import com.Atos.Webaccess.User.Entities.Hpassword;
import com.Atos.Webaccess.User.Entities.*;
import com.Atos.Webaccess.User.Services.HpasswordService;
import com.Atos.Webaccess.User.Services.UserService;




public class BasicTokenAuthenticationFilter extends BasicAuthenticationFilter{


	private final ParametreService parametreService;
	private final HpasswordService hpasswordService;
	
	public BasicTokenAuthenticationFilter(AuthenticationManager authenticationManager,
			AuthTokenService authTokenService ,UserService userService ,
			BCryptPasswordEncoder passwordEncoder,ParametreService parametreService,HpasswordService hpasswordService) {
		super(authenticationManager);
		this.parametreService= parametreService;
		this.hpasswordService=hpasswordService;
	}

	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
																		  Authentication authentication) throws IOException
	{		
		Date time = new Date();
		System.out.println(time+" BasicTokenAuthenticationFilter.onSuccessfulAuthentication");
		
			User user = (User) authentication.getPrincipal();
			request.getSession().setAttribute(USER_KEY, user);
			request.getSession().setAttribute(USER_ID, user.getId());
			
			
			if (user.getFirst_login() == 0) {
				request.getSession().setAttribute(FIRST_LOGIN, true);
				return;
			}else {
				request.getSession().setAttribute(FIRST_LOGIN, false); 
				
				if(user.getStatus() == 0) {
					request.getSession().setAttribute(WRONG_STATUS, "true");
					return;
				}
				else {
					Commun cm = new Commun();
					Parametre parametre = parametreService.getParametreByBanque(cm.readProp(DOMAIN_LIBELLE_AGENCE_PILOTE));
					if(parametre.getHpassword()== 1) {
					
						int nbDays = parametre.getpWvalidity();
						
						
			
						
						List<Hpassword> hpassword = hpasswordService.getHpasswordByIdUser(user.getId());
						Date d1 = null;
						if(hpassword.size() > 0)
						  d1 = hpassword.get(0).getDatesaisie();
						
						if(d1 == null)
																	// UTILISATEUR
																	// n'existe
																	// pas
																	// dans
																	// la
																	// table
																	// HPASSWORD
																	// ->
																	// Insertion
						{
									try {
										/// save the new HPASSWORD  
										Hpassword h =new Hpassword();					
										h.setStatut(1);
										h.setDatesaisie(new Date());
										h.setMotdepasse(user.getPassword());
										h.setIdutilisateur((int)user.getId());
										hpasswordService.saveHpassword(h);
										
									} catch (Exception e) {
										System.out
												.println("CAS : UTILISATEUR N'EXISTE PAS DANS HPASSWORD -> PROBLEME D'INSERTION DU PWD");
										e.printStackTrace();
									}
									d1 = new Date();

									 	/// USER NEW DATE AS  THE NEW PASSWORD DATESAISI 
									
						}
						
						
						int difDay = cm
								.differenceBetweenTwoDate(d1);		
						int validite = nbDays - difDay;
						
				
						if (validite < 0) {// MOT DE PASSE PW_EXPIRED
							request.getSession().setAttribute(PW_EXPIRED, "true");
							System.out
									.println("#### MOT DE PASSE INVALIDE -> MODIFICATION DU MOT DE PASSE OBLIGATOIRE ####");
							return;
						}	
						
					}
					else {
						System.out
								.println("################ HISTORIQUE DES MOTS DE PASSE INACTIF  ##############");
					}
								
				}
				
			}		

			
						
	}
	
	@Override
	protected void onUnsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
					throws IOException {
		System.out.println(" BasicTokenAuthenticationFilter.onUnsuccessfulAuthentication");
		/*Date time = new Date();
		System.out.println(time+" BasicTokenAuthenticationFilter.onUnsuccessfulAuthentication");
		HashMap<String, Object> data= new HashMap<String,Object>();
		User user = new User();
		String authorization = request.getHeader(AUTHORIZATION);	
		authorization = authorization.substring(6,authorization.length());		
		String decodedString = new String(Base64.decodeBase64(authorization));
		final String[] values = decodedString.split(":", 2);
		String credentialsUsername=values[0];
		String credentialsPw = values[1];
		System.out.println(" wrong username and password: "+credentialsUsername+" "+credentialsPw);
		user.setUsername(credentialsUsername);*/
		
		/*try {
			 user = this.userService.getUtilisateurByPseudoAndPassword(user);
				
			}catch (Exception e) {
				e.printStackTrace();
				/* #################### PBM ACCES BD ##################### */
				 /*data.put(RESPONSE_MESSAGE_KEY, "BD_ACCESS_PBM");
				 response.getOutputStream()
			        .println(objectMapper.writeValueAsString(data));
				 return;
			}*/
		
		/*if( user == null ) {
			/* #################### PSEUDO INEXISTANT ##################### */
			/*data.put(RESPONSE_MESSAGE_KEY, "PSEUDO_INEXISTANT");
			//response.getOutputStream()
	        .println(objectMapper.writeValueAsString(data));
		}else {
			/* #################### PSEUDO INEXISTANT ##################### */
			
		
			//String password = this.passwordEncoder.encode(CredentialsPw);			
			/*if(!this.passwordEncoder.matches(credentialsPw,user.getPassword())) {
			data.put(RESPONSE_MESSAGE_KEY, "WRONG_PW");
			response.getOutputStream()
	        .println(objectMapper.writeValueAsString(data));
			

			}	
			
		}
	

	*/}
	
	
	
}
