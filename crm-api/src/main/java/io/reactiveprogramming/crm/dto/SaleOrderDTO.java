package io.reactiveprogramming.crm.dto;

import java.util.Calendar;
import java.util.Set;

public class SaleOrderDTO {
	
	private Long id;
	private String customerName;
	private String customerEmail;
	private String refNumber;
	private Set<OrderLineDTO> orderLines;
	private Float total;
	private String registDate;
	
	
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
	public Set<OrderLineDTO> getOrderLines() {
		return orderLines;
	}
	public void setOrderLines(Set<OrderLineDTO> orderLines) {
		this.orderLines = orderLines;
	}
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	
}
