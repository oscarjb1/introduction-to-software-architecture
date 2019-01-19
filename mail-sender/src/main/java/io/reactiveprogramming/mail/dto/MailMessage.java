package io.reactiveprogramming.mail.dto;

import java.io.Serializable;

public class MailMessage implements Serializable{
	
	private String to;
	private String title;
	private String message;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
