package io.reactiveprogramming.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CUSTOMERS")
public class Customer {

	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="USERNAME", nullable=false)
	private String username;
	
	@Column(name="email", nullable=false)
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
