package io.reactiveprogramming.crm.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="SALES_ORDERS")
public class SaleOrder {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="CUSTOMER_NAME", length=250, nullable=false)
	private String customerName;
	
	@Column(name="CUSTOMER_EMAIL", length=100, nullable=false)
	private String customerEmail;
	
	@Column(name="REF_NUMBER")
	private String refNumber;
	
	@OneToMany(mappedBy="saleOrder", cascade=CascadeType.ALL)
	private Set<OrderLine> orderLines;
	
	
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


	public Set<OrderLine> getOrderLines() {
		return orderLines;
	}


	public void setOrderLines(Set<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	
}
