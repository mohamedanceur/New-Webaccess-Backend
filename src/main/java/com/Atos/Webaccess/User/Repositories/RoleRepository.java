package com.Atos.Webaccess.User.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Atos.Webaccess.User.Entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findRoleByRole(String role);
}
