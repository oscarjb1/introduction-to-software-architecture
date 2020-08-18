package io.reactiveprogramming.webhook.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.reactiveprogramming.commons.exceptions.GenericServiceException;
import io.reactiveprogramming.commons.exceptions.ValidateServiceException;
import io.reactiveprogramming.webhook.dao.IListenerDAO;
import io.reactiveprogramming.webhook.dto.ListenerDTO;
import io.reactiveprogramming.webhook.dto.MessageDTO;
import io.reactiveprogramming.webhook.entity.Listener;
import io.reactiveprogramming.webhook.enums.EventType;

@Service
public class EventListenerService {
	
	@Autowired
	private IListenerDAO listenerDAO;
	
	public void addListener(ListenerDTO listener)throws ValidateServiceException, GenericServiceException {
		try {
			Listener newListener = new Listener();
			newListener.setEndpoint(listener.getEndpoint());
			newListener.setEventType(EventType.valueOf(listener.getEventType()));
			newListener.setId(listener.getId());
			listenerDAO.save(newListener);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GenericServiceException("Internal Server error");
		}
	}
	
	public void pushMessage(MessageDTO message)throws ValidateServiceException, GenericServiceException {
		try {
			List<Listener> listeners = listenerDAO.findByEventType(message.getEventType());
			for(Listener listener : listeners) {
				try {
					RestTemplate templete = new RestTemplate();
					Map map = new HashMap<>();
					System.out.println("Send notify to ==> " + listener.getEndpoint());
					System.out.println("With body ==> " + message.getMessage());
					templete.postForObject(listener.getEndpoint(), message.getMessage(), Void.class, map);
				} catch (Exception e) {
					e.printStackTrace();
					throw new ValidateServiceException("Error to send message to " + listener.getEndpoint());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ValidateServiceException("Error to send messages");
		}
	}
}
