package io.reactiveprogramming.mail.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactiveprogramming.commons.email.EmailDTO;
import io.reactiveprogramming.mail.services.MailSenderService;

@RestController
@RequestMapping(value = "emails")
public class EmailController {
	
	@Autowired
	private MailSenderService emailSenderService;
	
	@PostMapping(value = "send")
	public void sendEmail(@RequestBody EmailDTO message) {
		emailSenderService.sendSimpleMessage(message);
	}
}
