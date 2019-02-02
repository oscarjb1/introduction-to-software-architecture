package io.reactiveprogramming.webhook.dto;

import io.reactiveprogramming.webhook.enums.EventType;

public class MessageDTO {
	
	public EventType eventType;
	public Object message;
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
}
