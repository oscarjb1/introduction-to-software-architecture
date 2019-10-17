package io.reactiveprogramming.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

import io.reactiveprogramming.mail.rabbit.RabbitReceiver;

@SpringBootApplication
@EnableEurekaClient
public class MailSenderApplication {

	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", MailSenderApplication.class.getResource("/trustore.jks").getFile());
        System.setProperty("javax.net.ssl.trustStorePassword", "1234");
        
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
    
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
    

}