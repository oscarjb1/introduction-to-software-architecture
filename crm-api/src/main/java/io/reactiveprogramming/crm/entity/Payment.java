package io.reactiveprogramming.crm.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PAYMENTS")
public class Payment {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="FK_SALEORDER", nullable=false)
	private SaleOrder saleOrder;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PAY_DATE")
	private Calendar paydate;
	
	@Enumerated(EnumType.STRING)
	@Column(name="PAYMENT_METHOD", nullable=false, length=20)
	private PaymentMethod paymentMethod;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SaleOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(SaleOrder saleOrder) {
		this.saleOrder = saleOrder;
	}

	public Calendar getPaydate() {
		return paydate;
	}

	public void setPaydate(Calendar paydate) {
		this.paydate = paydate;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}
