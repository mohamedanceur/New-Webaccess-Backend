package com.Atos.Webaccess.User.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Atos.Webaccess.User.Entities.Hpassword;
import java.util.Date;
import java.util.List;

@Repository
public interface HpasswordRepository  extends JpaRepository<Hpassword, Long>{
	List<Hpassword> findByIdutilisateur(long id);
}
