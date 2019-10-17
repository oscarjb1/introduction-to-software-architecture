package io.reactiveprogramming.commons.email;

import java.io.Serializable;

public class EmailDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String to;
	public String from;
	public String subject;
	public String message;
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
