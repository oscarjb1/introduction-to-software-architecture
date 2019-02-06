package io.reactiveprogramming.crm.dto;

import java.util.Calendar;

import io.reactiveprogramming.crm.entity.PaymentMethod;
import io.reactiveprogramming.crm.entity.SaleOrder;

public class PaymentDTO {
private Long id;
	
	private String paydate;
	private String paymentMethod;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}
