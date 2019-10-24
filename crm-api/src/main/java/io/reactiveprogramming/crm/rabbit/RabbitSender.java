package io.reactiveprogramming.crm.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {
	
    @Autowired
    private RabbitTemplate template;

    public void send(Object message) {
        template.convertAndSend("emails", "*", message);
    } 
    
    public void send(String exchange, String routingKey, Object payload) {
        template.convertAndSend(exchange, routingKey, payload);
    } 

}