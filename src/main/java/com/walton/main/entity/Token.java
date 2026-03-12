package com.walton.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "api_tokens")
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_token_seq")
	@SequenceGenerator(name = "api_token_seq", sequenceName = "API_TOKEN_SEQ", allocationSize = 1)
	private Long id;

    @Column(unique = true, length = 1000)
    private String token;

    private boolean revoked; 
    private boolean expired;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ApiUsers user;

	public Token() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Token(Long id, String token, boolean revoked, boolean expired, ApiUsers user) {
		super();
		this.id = id;
		this.token = token;
		this.revoked = revoked;
		this.expired = expired;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public ApiUsers getUser() {
		return user;
	}

	public void setUser(ApiUsers user) {
		this.user = user;
	}
    
    
    
}
