package io.reactiveprogramming.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;

public class RabbitMQConfig {

    @Bean
	public Queue emailsQueue() {
		return new Queue("newOrders");
	}
}