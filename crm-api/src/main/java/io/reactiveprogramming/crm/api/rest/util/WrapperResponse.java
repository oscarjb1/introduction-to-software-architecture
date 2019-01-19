package io.reactiveprogramming.crm.api.rest.util;

import java.io.Serializable;

public class WrapperResponse implements Serializable{
	
	private boolean ok;
	private String message;
	private Object body;
	
	
	public WrapperResponse() {
		
	}
	
	public WrapperResponse(boolean ok) {
		this.ok = ok;
	}
	
	public WrapperResponse(boolean ok, String message) {
		this.ok = ok;
		this.message = message;
	}
	
	public WrapperResponse(boolean ok, String message, Object body) {
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
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	
	
}
