package io.reactiveprogramming.mail.rabbit;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.reactiveprogramming.commons.email.EmailDTO;
import io.reactiveprogramming.mail.services.MailSenderService;

@Service
public class RabbitReceiver {
	
	@Autowired
	private MailSenderService mailService;

	@RabbitListener(queues = "emails")
	public void receive1(EmailDTO message) throws InterruptedException {
		System.out.println("newMessage => " + ReflectionToStringBuilder.toString(message, ToStringStyle.MULTI_LINE_STYLE));
		mailService.sendSimpleMessage(message);
	}
}
