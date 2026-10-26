package com.Atos.Webaccess.User.Controllers;

import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Atos.Webaccess.User.Entities.*;
import com.Atos.Webaccess.User.Services.*;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@PostMapping("/getAll")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public  List<Role> getAllroles(HttpSession session){
		return roleService.findAll();
		
	}
	
	
	@PostMapping("/addRole")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public  Role addRole(@RequestBody Role newRole){
		return roleService.addRole(newRole);
		
	}
	
	@PostMapping("/updateRole")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public  boolean updateRole(@RequestBody Role newRole){

		return roleService.updateRole(newRole);
		
	}
	
	@PostMapping("/deleteRole")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public  int deleteRole(@RequestBody String   idRole ){
		return roleService.deleteRoleById(idRole);
		
	}
	
	
	
	
	
	
	
}
