
package io.reactiveprogramming.crm.api;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import io.reactiveprogramming.crm.rabbit.RabbitSender;

@SpringBootApplication
@EnableEurekaClient
//@EnableAutoConfiguration
@EntityScan("io.reactiveprogramming.crm.entity")
//@IntegrationComponentScan
//@EnableIntegration
//@ComponentScan
@Configuration
public class CrmApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmApiApplication.class, args);
	} 
	
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Bean
    public RabbitSender sender() { 
        return new RabbitSender();
    }
	
	@Bean
    public Queue topic() {
        return new Queue("emails");
    }
	
	@Bean
	public MessageConverter jsonMessageConverter(){
	    return new Jackson2JsonMessageConverter();
	}
    
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
	    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	    rabbitTemplate.setMessageConverter(jsonMessageConverter());
	    return rabbitTemplate;
	}
}

