package com.Atos.Webaccess.User.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import static com.Atos.Webaccess.Constants.AppConstants.*;

import com.Atos.Webaccess.Commun.Commun;
import com.Atos.Webaccess.User.Entities.PasswordResetModel;
import com.Atos.Webaccess.User.Entities.User;
import com.Atos.Webaccess.User.Enums.RoleType;
import com.Atos.Webaccess.User.Repositories.UserRepository;
//import com.atos.security.domain.effets.EffetRepository;
import com.Atos.Webaccess.Domain.Groupes.Groupe;
import com.Atos.Webaccess.Domain.Groupes.GroupeRepository;
//import com.Atos.Webaccess.Security.*;

@Service
public class UserServiceImpl implements UserService {

	private final String DEFAULT_PASSWORD = "defpwd12341234";
	private final Integer FALSE = 0;
	private final UserRepository repository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final RoleService roleService;

	private GroupeRepository groupeRepository;

	// @Autowired
	// EffetRepository effetRepository;

	@Qualifier("mainJdbcTemplate")final JdbcTemplate mainJdbcTemplate;

	public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder, RoleService roleService, JdbcTemplate mainJdbcTemplate) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
		this.mainJdbcTemplate = mainJdbcTemplate;
		// this.seed();
	}

	@Override
	public void seed() {

		if (this.repository.count() > 0)
			return;
		Commun cmn = new Commun();
		final String password = this.passwordEncoder.encode(DEFAULT_PASSWORD);
		final User admin = new User();
		admin.setPassword(password);
		admin.setUsername("admin");
		admin.setDefaultipadress("169.254.243.141");
		admin.setIdGroupe(1);
		admin.setIdBanque(1);
		admin.setDefault_Code_Agence(cmn.readProp(DOMAIN_CODE_AGENCE_PILOTE));
		admin.setEmail("admin@atos.net");
		admin.setMobile("0556565656");
		admin.setNom("admin");
		admin.setPrenom("admin");
		admin.setStatus(STATUS_ACTIF_TRUE);
		admin.setFirst_login(STATUS_ACTIF_FALSE);
		List<GrantedAuthority> adminAuthorites = new ArrayList<GrantedAuthority>();
		adminAuthorites.add(this.roleService.find(RoleType.ROLE_ADMIN));
		adminAuthorites.add(this.roleService.find(RoleType.ROLE_USER));
		admin.setAuthorites(adminAuthorites);

		final User user = new User();
		user.setPassword(password);
		user.setUsername("user");
		user.setDefaultipadress("169.254.243.166");
		user.setIdGroupe(1);
		user.setIdBanque(1);
		user.setEmail("user@atos.net");
		user.setMobile("0550505050");
		user.setDefault_Code_Agence(cmn.readProp(DOMAIN_CODE_AGENCE_PILOTE));
		user.setStatus(STATUS_ACTIF_TRUE);
		user.setFirst_login(STATUS_ACTIF_FALSE);
		user.setNom("user");
		user.setPrenom("user");
		List<GrantedAuthority> userAuthorites = new ArrayList<GrantedAuthority>();
		userAuthorites.add(this.roleService.find(RoleType.ROLE_USER));
		user.setAuthorites(userAuthorites);

		this.repository.save(admin);
		this.repository.saveAndFlush(user);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.repository.findUserByUsername(username);

		if (user != null) {
			return user;
		}

		throw new UsernameNotFoundException("User not found with username " + username);
	}

	public List<User> getAllUsers() {

		List<User> users = this.repository.findAll();
		if (users != null) {
			return users;
		}
		return null;

	}

	public User addUser(User user) {
		if (this.repository.existsByUsernameOrEmail(user.getUsername(), user.getEmail()))
			return user;
		else {
			String password = this.passwordEncoder.encode(user.getPassword());
			user.setPassword(password);
			List<GrantedAuthority> userAuthorites = new ArrayList<GrantedAuthority>();

			for (int i = 0; i < user.getAuthoritesList().size(); i++) {
				userAuthorites.add(this.roleService.find(user.getAuthoritesList().get(i)));
			}
			user.setAuthorites(userAuthorites);

			return this.repository.save(user);
		}

	}

	@Override
	public boolean deleteUserById(String idUser) {

		if (this.deleteFromAuth_tokensById(idUser) != RETURN_SUCCES) {
			return false;
		} else if (this.deleteFromUsers_rolesById(idUser) != RETURN_SUCCES) {
			return false;
		} else {
			try {
				this.repository.deleteById(Long.valueOf(idUser));
				return true;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	public boolean UpdateUser(User user) {

		if (this.repository.findAll().stream().filter(u -> u.getId() != user.getId()
				&& (u.getUsername() == user.getUsername() || u.getEmail() == user.getEmail())).count() > 0)
			return false;

		else {
			List<GrantedAuthority> userAuthorites = new ArrayList<GrantedAuthority>();
			for (int i = 0; i < user.getAuthoritesList().size(); i++) {
				userAuthorites.add(this.roleService.find(user.getAuthoritesList().get(i)));
			}
			user.setAuthorites(userAuthorites);

			this.repository.saveAndFlush(user);
			return true;

		}
	}

	public int resetUserPassword(PasswordResetModel resetFormModel) {
		System.out.println("start reset");
		try {
			User user = this.repository.findUserByUsername(resetFormModel.getUserName());
			if (this.passwordEncoder.matches(resetFormModel.getOldPassword(), user.getPassword())) {
				String password = this.passwordEncoder.encode(resetFormModel.getNewPassword());
				System.out.println("old password :" + user.getPassword());
				user.setPassword(password);
				user.setFirst_login(1);
				System.out.println("new password :" + user.getPassword());
				this.repository.saveAndFlush(user);
				// return "Success";
				return RETURN_SUCCES;
			} else {

				System.out.println(" reset password dont match");

				return OLD_PASSWORD_NOT_MATCHED; // "oldPasswordNotMatchesException";
			}
		} catch (Exception e) {
			System.out.println("reset exeption");
			/*
			 * e.printStackTrace(); return "otherException";
			 */
			if (e.getCause().toString().contains("ConstraintViolationException"))
				return RETURN_ERROR_CONSTRAINT;
			else
				return RETURN_ERROR_DATABASE;
		}

	}

	// --- domain

	@Override
	public User getUtilisateurConfigById(long idUser) {

		return this.repository.findById(idUser).orElse(null);

	}

	@Override
	public Collection<? extends GrantedAuthority> getUtilisateurAuthoritiesById(long idUser) {

		return this.repository.findById(idUser).get().getAuthorities();

	}

	@Override
	public User registerUtilisateur(User utilisateur) {

		return this.repository.saveAndFlush(utilisateur);

	}

	@Override
	public User getUtilisateurByPseudoAndPassword(User utilisateur) {

		return this.repository.findUserByUsername(utilisateur.getUsername());
	}

	public boolean lockUnlockUser(long IDUTILISATEUR) {
		try {

			User legacyUser = new User();
			legacyUser = this.repository.findById(IDUTILISATEUR).orElse(null);
			if (legacyUser.getStatus() == FALSE)
				legacyUser.setStatus(1);
			else
				legacyUser.setStatus(0);
			this.repository.saveAndFlush(legacyUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean ResetPassword(long idUser) {
		try {

			User legacyUser = new User();
			legacyUser = this.repository.findById(idUser).orElse(null);
			legacyUser.setFirst_login(FALSE);
			final String password = this.passwordEncoder.encode(DEFAULT_PASSWORD);
			legacyUser.setPassword(password);
			this.repository.saveAndFlush(legacyUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Map<String, Object> login(User utilisateur) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		User user;
		try {
			user = this.repository.findUserByUsername(utilisateur.getUsername());

		} catch (Exception e) {
			e.printStackTrace();
			/* #################### PBM ACCES BD ##################### */
			hashMap.put(RESPONSE_MESSAGE_KEY, "BD_ACCESS_PBM");
			return hashMap;
		}

		if (user == null) {
			/* #################### PSEUDO INEXISTANT ##################### */
			hashMap.put(RESPONSE_MESSAGE_KEY, "PSEUDO INEXISTANT");
		} else {
			/* #################### PSEUDO INEXISTANT ##################### */

			if (this.passwordEncoder.matches(utilisateur.getPassword(), user.getPassword())) {

			}
		}
		return hashMap;
	}

	@Override
	public int checkEffetNotAttachedWithImage(long idUser) {
		// TODO Auto-generated method stub
		List<String> agences = new ArrayList<>();
		User user = new User();
		user = getUtilisateurConfigById(idUser);
		Groupe groupe = groupeRepository.findById(
			    (int) user.getIdGroupe()
			).orElse(null);

		if (groupe != null) {
			groupe.getAgencesGrp().forEach(g -> agences.add(g.getCode()));
		}

		try {
			// return lotRepository.findNombreEffetByAgence(agences);
			// int result =
			// effetRepository.findNombreEffetByAgence(user.getDefault_Code_Agence());
			// dertha commentaire 12/10/2026 bah ntasti 9bel ma n'ajouter effets
			int result = 1;
			return result;
		} catch (Exception e) {
			return 0;
		}

	}

	@Override
	public int deleteFromAuth_tokensById(String idUser) {
		try {
			mainJdbcTemplate.execute("DELETE FROM auth_tokens\r\n" + " WHERE user_id = " + idUser + "");
			return RETURN_SUCCES;
		} catch (Exception e) {

			if (e.getCause().toString().contains("ConstraintViolationException"))
				return RETURN_ERROR_CONSTRAINT;
			else
				return RETURN_ERROR_DATABASE;
		}
	}

	@Override
	public int deleteFromUsers_rolesById(String idUser) {
		try {
			mainJdbcTemplate.execute("DELETE FROM users_roles\r\n" + " WHERE user_id = " + idUser + "");
			return RETURN_SUCCES;
		} catch (Exception e) {

			if (e.getCause().toString().contains("ConstraintViolationException"))
				return RETURN_ERROR_CONSTRAINT;
			else
				return RETURN_ERROR_DATABASE;
		}
	}

	@Override
	public String getPasswordByUsername(String username) {
		return this.repository.getPasswordByUsername(username);
	}

}
