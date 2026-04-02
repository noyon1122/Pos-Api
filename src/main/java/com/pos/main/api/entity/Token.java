package com.pos.main.api.entity;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "api_tokens")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_token_seq")
	@SequenceGenerator(name = "api_token_seq", sequenceName = "API_TOKEN_SEQ", allocationSize = 1)
	private Long id;

    @Column(unique = true, length = 1000)
    private String accessToken;
    
    @Column(unique = true, length = 1000)
    private String refreshToken;

    private boolean revoked; 
    private boolean expired;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ApiUsers user;
}
