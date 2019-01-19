package io.reactiveprogramming.crm.api;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.reactiveprogramming.crm.rabbit.RabbitSender;

@SpringBootApplication
@EnableEurekaClient
@EntityScan("io.reactiveprogramming.crm.entity")
@Configuration
@IntegrationComponentScan
@EnableIntegration
@ComponentScan(basePackages = "io.reactiveprogramming.crm.ftp")
public class CrmApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmApiApplication.class, args);
	} 
	
	@Bean
    public RabbitSender sender() {
        return new RabbitSender();
    }
	
	@Bean
    public Queue topic() {
        return new Queue("emails");
    }

}

