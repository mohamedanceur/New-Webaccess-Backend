package com.Atos.Webaccess.User.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.Atos.Webaccess.User.Entities.Role;
import com.Atos.Webaccess.User.Enums.RoleType;
import com.Atos.Webaccess.User.Repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

	private final RoleRepository roleRepository;
	private final RightService rightService;
	private final int DataIntegrityViolationException=3;
	private final int Success=1;
	private final int Error=2;
	//@Autowired
	//@Qualifier("mainJdbcTemplate")
	//JdbcTemplate mainJdbcTemplate;
	
	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository ,RightService rightService ) {
		this.rightService = rightService;
		this.roleRepository = roleRepository;
		//this.initialize();
	}
	
	@Override
	public void initialize() {
		
		System.out.println("RoleServiceImpl");
		List<Role> list_roles = roleRepository.findAll();
		List<String> list_role_names = new ArrayList<String>();
		for (Role r : list_roles) list_role_names.add(r.getRole());
		
		for(RoleType roleType : RoleType.values()) {
			if (!list_role_names.contains(roleType.name())) {
			Role role = new Role();
			role.setRole(roleType.name());
			role.setRights(this.rightService.findAll());
			this.roleRepository.save(role);
			}
		}
	
		this.roleRepository.flush();
		

	}

	@Override
	public List<Role> findAll() {
		return this.roleRepository.findAll();
	}

	@Override
	public Role find(RoleType role) {
		return this.roleRepository.findRoleByRole(role.name());
	}
	
	
	@Override
	public Role find(String roleName) {
		return this.roleRepository.findRoleByRole(roleName);
	}

	@Override
	public Role addRole(Role r) {
		// TODO Auto-generated method stub
		return this.roleRepository.save(r);
	}
	
	@Override
	public int deleteRoleById (String idRole) {
		try {
			this.roleRepository.deleteById(Long.valueOf(idRole));
			return Success;   // true
		} catch (Exception e) {
			if(e.toString().contains("DataIntegrityViolationException"))
				return DataIntegrityViolationException;  // DataIntegrityViolationException
			return Error; // false
		}
	}

	@Override
	public boolean updateRole(Role newRole) {
		try {
			this.roleRepository.saveAndFlush(newRole);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	

	
}
