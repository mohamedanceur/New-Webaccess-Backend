package com.Atos.Webaccess.User.Controllers;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Atos.Webaccess.User.Entities.*;
import com.Atos.Webaccess.User.Services.*;


@RestController
@RequestMapping("/right")
public class RightController {
	
	
	@Autowired
	private RightService rightService;
	
	@PostMapping("/getAll")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	public  List<Right> getAllrights(HttpSession session){		
		return rightService.findAll();
		
	}
	
	
	

}
