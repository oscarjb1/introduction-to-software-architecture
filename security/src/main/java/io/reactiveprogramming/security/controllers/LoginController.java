package io.reactiveprogramming.security.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


	@RequestMapping("/sso")
	public String welcome(Map<String, Object> model) {
		model.put("message", "hola mundo");
		return "sso";
	}

}