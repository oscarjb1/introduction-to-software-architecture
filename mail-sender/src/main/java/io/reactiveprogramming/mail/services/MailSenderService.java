package io.reactiveprogramming.mail.services;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import io.reactiveprogramming.mail.dto.MailMessage;

@Component
public class MailSenderService {
	@Autowired
    public JavaMailSender emailSender;
 
    public void sendSimpleMessage(MailMessage message) {
    	System.out.println("mailSender => " + ReflectionToStringBuilder.toString(message, ToStringStyle.MULTI_LINE_STYLE));
		SimpleMailMessage mail = new SimpleMailMessage(); 
		mail.setTo(message.getTo());
		mail.setSubject(message.getTitle()); 
		mail.setText(message.getMessage());
		emailSender.send(mail);
    }
}
