package com.Atos.Webaccess.User.Repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Atos.Webaccess.User.Entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public User findUserByUsername(String username);

	public boolean existsByUsernameOrEmail(String username, String email);

	public boolean existsByUsername(String username);

	public List<User> findByIdGroupe(long idGroupe);

//      nom_utilisateur like :NOM_UTILISATEUR and etatlot != :ETATLOT and etatlot !=0 and etatlot !=5 and etatlot !=3 and etatlot !=7
	@Query("select u.username from User u where id = :idutilisateur")
	public String findUsernameById(@Param("idutilisateur") long idUtilisateur);
	// public User findById(long id);

	@Query("select u.default_Code_Agence from User u where id = :idutilisateur")
	public String getCodeAgenceByUserId(@Param("idutilisateur") long idUtilisateur);

	@Transactional
	@Query(value = "select password from users where username = :name", nativeQuery = true)
	public String getPasswordByUsername(@Param("name") String username);

}
