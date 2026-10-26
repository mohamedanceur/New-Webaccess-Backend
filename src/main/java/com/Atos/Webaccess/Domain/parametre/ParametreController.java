package com.Atos.Webaccess.Domain.parametre;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/parametre")
public class ParametreController {
	
	@Autowired
	private ParametreService  parametreService ;
	
	
	@PostMapping("/updateParametre")
	//@PreAuthorize("hasRole('ROLE_USER')")
	public int updateParametre(@RequestBody Parametre parametre) {
		return parametreService.updateParametre(parametre);
	}
	
	@PostMapping("/getParametre")
	//@PreAuthorize("hasRole('ROLE_USER')")
	public Parametre getParametre(HttpSession session) {
		return parametreService.getParametreByBanque("");
	}

	@PostMapping("/getCodeBanque")
	public String getParametre(String id) {
		
		return "{\"code\":\""+parametreService.getParametreByBanque("").getCodeBanque()+"\"}";
	}
}
