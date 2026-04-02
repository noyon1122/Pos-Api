package com.pos.main.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankResponse {

	private int statusCode;
	private String requestId;
	private String message;
	
}
