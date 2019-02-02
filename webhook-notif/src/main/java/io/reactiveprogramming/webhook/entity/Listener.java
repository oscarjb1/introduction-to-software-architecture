package io.reactiveprogramming.webhook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.reactiveprogramming.webhook.enums.EventType;

@Entity
@Table(name="LISTENERS")
public class Listener {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ENDPOINT", nullable=false, length=500)
	private String endpoint;
	
	@Column(name="EVENT_TYPE", nullable=false, length=20)
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
}
