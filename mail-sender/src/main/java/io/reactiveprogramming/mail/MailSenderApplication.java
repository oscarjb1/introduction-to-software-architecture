package io.reactiveprogramming.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.*;


import io.reactiveprogramming.mail.rabbit.RabbitReceiver;

@SpringBootApplication
@EnableEurekaClient
public class MailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailSenderApplication.class, args);
	}
	
	@Bean
    public RabbitReceiver receiver() {
        return new RabbitReceiver();
    }

    @Bean
    public Queue autoDeleteQueue1() {
        return new Queue("emails", true, false, false);
    }
    

}