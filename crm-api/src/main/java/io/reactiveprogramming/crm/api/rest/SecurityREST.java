package io.reactiveprogramming.crm.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.crm.api.services.SecurityService;
import io.reactiveprogramming.crm.dto.LoginDTO;
import io.reactiveprogramming.crm.dto.LoginResponseDTO;

@RestController()
@RequestMapping("security")
public class SecurityREST {
	
	@Autowired
	private SecurityService securityService;

	@PostMapping(path="login")
	public WrapperResponse login(@RequestBody LoginDTO loginDTO) {
		try {
			LoginResponseDTO response = this.securityService.login(loginDTO);
			return new WrapperResponse(true, "", response);
		} catch (Exception e) {
			return new WrapperResponse(false, e.getMessage());
		}
	}
}
