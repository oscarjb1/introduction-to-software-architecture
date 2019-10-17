package io.reactiveprogramming.crm.dto;


public class MessageDTO {
	
	public EventType eventType;
	public Object message;
	
	public enum EventType {
		NEW_SALES
	}
	
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
