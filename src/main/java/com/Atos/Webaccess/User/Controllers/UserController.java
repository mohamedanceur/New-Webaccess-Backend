package com.Atos.Webaccess.User.Controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Atos.Webaccess.User.Entities.*;
import com.Atos.Webaccess.User.Services.*;

@RestController
@RequestMapping("/utilisateur")
public class UserController {
	
	

	@Autowired
	private UserService userService;
	
	

	@PostMapping("/getAll")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public  List<User> getAllUsers(HttpSession session){		
		return userService.getAllUsers();
		
	}
	
	@PostMapping("/adduser")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public  User addUser(@RequestBody User  user ){
		return userService.addUser(user);
		
	}
	
	@PostMapping("/deleteUser")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public  boolean deleteUser(@RequestBody String   idUser ){
		
		return userService.deleteUserById(idUser);

	}
	
	@PostMapping("/updateUser")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public  boolean updateUser(@RequestBody User  user  ){
		
		return userService.UpdateUser(user);

	}
	
	@PostMapping("/resetPassword")
	//@PreAuthorize("hasRole('ROLE_USER')")
	public  int resetPassword(@RequestBody PasswordResetModel resetFormModel){
		return userService.resetUserPassword(resetFormModel);	 
	}
	
	/*@PostMapping("/temporaryConfiguration")
	@PreAuthorize("hasRole('ROLE_USER')")
	public  Map<String, Object>  temporaryConfiguration(@RequestBody TemporaryConfiguration temporaryConfiguration){
		return new HashMap<String,Object>() {{
			put("msg", configurationService.createConfigurationFile(temporaryConfiguration));
		}};
		
		
		 
	}*/
	@PostMapping("/login")
//	@PreAuthorize("hasRole('ROLE_USER')")
	public  Map<String, Object> login(@RequestBody User  user ){
		return userService.login(user);
		 
	}
	
	
	@PostMapping("/getuserByName")
	//@PreAuthorize("hasRole('ROLE_USER')")
	public  User   getuserByName(@RequestBody User  user ){
		return userService.getUtilisateurByPseudoAndPassword(user);
		 
	}
	
	@PostMapping("/getuserByID")
	//@PreAuthorize("hasRole('ROLE_USER')")
	public  User getuserByID(@RequestBody long   idUser){
		return userService.getUtilisateurConfigById(idUser);
		 
	}
	@PostMapping("/getUtilisateurAuthoritiesById")
	//@PreAuthorize("hasRole('ROLE_USER')")
	public  Collection<? extends GrantedAuthority> getUtilisateurAuthoritiesById(@RequestBody long   idUser){
		return userService.getUtilisateurAuthoritiesById(idUser);
		 
	}
	
	@PostMapping("/LockUnlock")
	//@PreAuthorize("hasRole('ROLE_USER')")
	public  boolean   getuserByName(@RequestBody long   idUser ){
		return userService.lockUnlockUser(idUser);
		 
	}
	
	@PostMapping("/resetDefaultPassword")
	//@PreAuthorize("hasRole('ROLE_USER')")
	public  boolean   ResetPassword(@RequestBody long   idUser ){
		return userService.ResetPassword(idUser);
		 
	}
	
	@PostMapping("/checkEffet")
	//@PreAuthorize("hasRole('ROLE_USER')")
	public  int   checkEffet(@RequestBody int idutilisateur ){
		
		//return (int) ((Math.random() * (11 - 2)) + 2);
		return userService.checkEffetNotAttachedWithImage(idutilisateur);
		 
	}
}
