package com.Atos.Webaccess.User.Services;

import java.util.List;

import com.Atos.Webaccess.User.Entities.Role;
import com.Atos.Webaccess.User.Enums.RoleType;


public interface RoleService {

	void initialize();
	
	public List<Role> findAll();
	public Role find(RoleType role);
	public Role find(String roleName );
	 public Role addRole(Role r );
	 public int deleteRoleById(String idRole);

	public boolean updateRole(Role newRole);
}


