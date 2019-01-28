package io.reactiveprogramming.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class User {
	
	@Id
	@Column(name="USERNAME", length=50)
	private String username;
	
	@Column(name="PASSWORD", nullable=false, length=50)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ROL", nullable=false)
	private Rol rol;
	
	public static enum Rol{
		ADMIN,
		OPERATOR
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
}
