package io.reactiveprogramming.security.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.reactiveprogramming.commons.dto.LoginDTO;
import io.reactiveprogramming.commons.dto.LoginResponseDTO;
import io.reactiveprogramming.commons.rest.WrapperResponse;
import io.reactiveprogramming.security.services.SecurityService;


@RestController()
public class SecurityREST {
	
	@Autowired
	private SecurityService securityService;
	
	@PostMapping(path="login")
	public ResponseEntity<WrapperResponse<LoginResponseDTO>> login(@RequestBody LoginDTO loginDTO) {
		try {
			LoginResponseDTO response = this.securityService.login(loginDTO);
			return ResponseEntity.ok(new WrapperResponse(true, "", response));
		} catch (Exception e) {
			return ResponseEntity.ok(new WrapperResponse(false, e.getMessage()));
		}
	}
	
	@RequestMapping(value = "loginForm", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView loginForm(@RequestParam Map<String, String> params) {
		try {
			return new ModelAndView("WEB-INF/vistas/sso?token=xxx");
		} catch (Exception e) {
			return new ModelAndView("sso?error=Invalid user or password");
		}
	}
	
	@GetMapping(path="token/validate")
	public WrapperResponse<LoginResponseDTO> tokenValidate(@RequestParam("token") String token) {
		try {
			LoginResponseDTO user = securityService.decriptToken(token);
			return new WrapperResponse(true, "", user);
		} catch (Exception e) {
			return new WrapperResponse(false, e.getMessage());
		}
		
	}
}
