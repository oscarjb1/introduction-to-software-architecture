package io.reactiveprogramming.mail.services;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import brave.Tracer;
import io.reactiveprogramming.commons.email.EmailDTO;

@Service
public class MailSenderService {
	
	private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class.getCanonicalName());
	
	@Autowired
    public JavaMailSender emailSender;
	
	@Autowired
	private Tracer tracer;
	
 
    public void sendSimpleMessage(EmailDTO message) {
    	logger.info("mailSender => " + ReflectionToStringBuilder.toString(message, ToStringStyle.MULTI_LINE_STYLE));
    	tracer.currentSpan().tag("mail.new", ReflectionToStringBuilder.toString(message, ToStringStyle.MULTI_LINE_STYLE));
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
			logger.error(e.getMessage());
			tracer.currentSpan().tag("mail.error", e.getMessage());
		}
    }
}
