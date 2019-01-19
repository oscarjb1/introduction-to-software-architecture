package io.reactiveprogramming.crm.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable{
	
	private Long id;
	private String name;
	private Float price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}
}
