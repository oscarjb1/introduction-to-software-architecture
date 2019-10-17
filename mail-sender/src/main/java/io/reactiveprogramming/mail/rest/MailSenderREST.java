package io.reactiveprogramming.mail.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.reactiveprogramming.commons.email.EmailDTO;
import io.reactiveprogramming.mail.dto.MailMessage;
import io.reactiveprogramming.mail.services.MailSenderService;

@RestController("mail")
public class MailSenderREST {

	
	@Autowired
	private MailSenderService mailSenderService;
	
	@PostMapping
	public void sendMail(@RequestBody EmailDTO message) {
		mailSenderService.sendSimpleMessage(message);
	}
}
