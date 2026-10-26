package com.Atos.Webaccess.User.Services;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.Atos.Webaccess.User.Entities.PasswordResetModel;
import com.Atos.Webaccess.User.Entities.User;


public interface UserService extends UserDetailsService {
	
	void seed();
	public List<User>  getAllUsers();
	public User addUser(User user);
	public boolean deleteUserById(String idUser);
    public 	boolean UpdateUser(User user);
    public int resetUserPassword(PasswordResetModel resetFormModel);
    
    //------domain
	public User getUtilisateurConfigById(long idUser);
	public User registerUtilisateur(User utilisateur);
	public User getUtilisateurByPseudoAndPassword(User utilisateur);
	public Map<String, Object> login(User utilisateur);
	public boolean lockUnlockUser(long IDUTILISATEUR);
	public boolean ResetPassword(long idUser);
	public int checkEffetNotAttachedWithImage(long idUser);
	Collection<? extends GrantedAuthority> getUtilisateurAuthoritiesById(long idUser);
	public int deleteFromAuth_tokensById(String idUser);
	public int deleteFromUsers_rolesById(String idUser);
	public String getPasswordByUsername (String username);
}
