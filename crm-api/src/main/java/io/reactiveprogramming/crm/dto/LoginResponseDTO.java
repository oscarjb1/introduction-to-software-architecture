package io.reactiveprogramming.crm.dto;

import io.reactiveprogramming.crm.entity.User;

public class LoginResponseDTO {
	private String username;
	private User.Rol rol;
	private String token;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public User.Rol getRol() {
		return rol;
	}
	public void setRol(User.Rol rol) {
		this.rol = rol;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
