package io.reactiveprogramming.commons.rest;

import java.io.Serializable;

public class WrapperResponse<T> implements Serializable{
	
	private boolean ok;
	private String message;
	private T body;
	
	
	public WrapperResponse() {
		
	}
	
	public WrapperResponse(boolean ok) {
		this.ok = ok;
	}
	
	public WrapperResponse(boolean ok, String message) {
		this.ok = ok;
		this.message = message;
	}
	
	public WrapperResponse(boolean ok, String message, T body) {
		this.ok = ok;
		this.message = message;
		this.body = body;
	}
	
	public boolean isOk() {
		return ok;
	}
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
	
	
}
