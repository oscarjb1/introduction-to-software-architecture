package io.reactiveprogramming.mail.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import io.reactiveprogramming.mail.services.MailSenderService;

public class RabbitReceiver {
	
	@Autowired
	private MailSenderService mailService;

	@RabbitListener(queues = "emails")
	public void receive1(String in) throws InterruptedException {
		System.out.println("newMessage => " + in);
		//mailService.sendSimpleMessage(message);
	}

}
