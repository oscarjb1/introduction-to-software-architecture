package io.reactiveprogramming.crm.dto;

import java.util.Set;

import io.reactiveprogramming.crm.entity.PaymentMethod;

public class NewOrderDTO {
	private Long id;
	private String customerName;
	private String customerEmail;
	private String refNumber;
	private Set<NewOrderLineDTO> orderLines;
	private String paymentMethod;
	private float total;
	private CardDTO card;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public Set<NewOrderLineDTO> getOrderLines() {
		return orderLines;
	}
	public void setOrderLines(Set<NewOrderLineDTO> orderLines) {
		this.orderLines = orderLines;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public CardDTO getCard() {
		return card;
	}
	public void setCard(CardDTO card) {
		this.card = card;
	}
}
