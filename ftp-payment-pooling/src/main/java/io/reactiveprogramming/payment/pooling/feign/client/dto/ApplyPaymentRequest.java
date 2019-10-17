package io.reactiveprogramming.payment.pooling.feign.client.dto;

public class ApplyPaymentRequest {
	private String refNumber;
	private float ammount;
	
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public float getAmmount() {
		return ammount;
	}
	public void setAmmount(float ammount) {
		this.ammount = ammount;
	}
	
}
