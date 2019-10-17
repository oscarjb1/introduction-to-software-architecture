package io.reactiveprogramming.mail.services;

import java.util.logging.Logger;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import io.reactiveprogramming.commons.email.EmailDTO;

@Component
public class MailSenderService {
	@Autowired
    public JavaMailSender emailSender;
	
	private static final Logger logger = Logger.getLogger(MailSenderService.class.getCanonicalName());
 
    public void sendSimpleMessage(EmailDTO message) {
    	System.out.println("mailSender => " + ReflectionToStringBuilder.toString(message, ToStringStyle.MULTI_LINE_STYLE));
    	
    	try {
        	MimeMessage messageHTML = emailSender.createMimeMessage();
        	message.setSubject(message.getSubject());
            
        	MimeMessageHelper helper = new MimeMessageHelper(messageHTML, true);
        	helper.setSubject(message.getSubject());
            helper.setFrom(message.getFrom());
            helper.setTo(message.getTo());
            helper.setText(message.getMessage(), true);
            
            emailSender.send(messageHTML);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
			
		}
    }
}
