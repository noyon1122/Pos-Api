package com.walton.main.dto;

public class AuthResponse {

	private int statusCode;
	private String token;
	private String message;
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AuthResponse(int statusCode, String token, String message) {
		super();
		this.statusCode = statusCode;
		this.token = token;
		this.message = message;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	
	
	
}
