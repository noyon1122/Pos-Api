package com.pos.main.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

	private int statusCode;
	private String accessToken;
	private String refreshToken;
	private String message;
	
}
