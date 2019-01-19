package io.reactiveprogramming.crm.rabbit;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.reactiveprogramming.crm.dto.ProductDTO;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate template;



    public void send() {
    	ProductDTO dto = new ProductDTO();
    	dto.setId(1L);
    	dto.setName("Name");
    	dto.setPrice(100f);
        template.convertAndSend("emails", "*", dto);
        System.out.println(" [x] Sent ==>");
    } 

}