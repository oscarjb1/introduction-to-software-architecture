package io.reactiveprogramming.crm.entity;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@Column(name="TOTAL", nullable= false)
	private Float total;
	
	@OneToMany(mappedBy="saleOrder", cascade=CascadeType.ALL)
	private Set<OrderLine> orderLines;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REGIST_DATE", nullable=false, updatable=false)
	private Calendar registDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS", nullable=false, length=10)
	private OrderStatus status;
	
	@OneToOne(mappedBy="saleOrder", cascade=CascadeType.ALL)
	private Payment payment;
	
	public OrderStatus getStatus() {
		return status;
	}


	public void setStatus(OrderStatus status) {
		this.status = status;
	}


	public Payment getPayment() {
		return payment;
	}


	public void setPayment(Payment payment) {
		this.payment = payment;
	}


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
		updateTotal();
		
	}
	
	
	public Float getTotal() {
		return total;
	}


	public void setTotal(Float total) {
		this.total = total;
	}


	public Calendar getRegistDate() {
		return registDate;
	}


	public void setRegistDate(Calendar registDate) {
		this.registDate = registDate;
	}


	private void updateTotal() {
		if(this.orderLines != null) {
			float total = 0;
			for(OrderLine line : this.orderLines) {
				total = line.getProduct().getPrice() * line.getQuantity();
			}
			this.total = total;
		}
		
	}
	
}
