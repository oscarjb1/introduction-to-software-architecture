package io.reactiveprogramming.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
	@RequestMapping("/sso")
	public String mensaje() {
		return "sso";
	}
}