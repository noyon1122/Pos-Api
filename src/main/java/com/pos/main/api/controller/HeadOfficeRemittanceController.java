package com.pos.main.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.main.api.dto.BankRequestDto;
import com.pos.main.api.dto.BankResponse;
import com.pos.main.api.service.HeadOfficeRemittanceService;

@RestController
@RequestMapping("/api/v1/")
public class HeadOfficeRemittanceController {


	private final HeadOfficeRemittanceService headOfficeRemittanceService;
	private final ObjectMapper objectMapper;

	

	public HeadOfficeRemittanceController(HeadOfficeRemittanceService headOfficeRemittanceService,
			ObjectMapper objectMapper) {
		super();

		this.headOfficeRemittanceService = headOfficeRemittanceService;
		this.objectMapper = objectMapper;
	}
	
	@PostMapping("/remittances")
	public ResponseEntity<BankResponse> add(
	        @RequestHeader("X-Walton-Signature") String signature,
	        @RequestBody String rawJson) throws JsonProcessingException {

	    BankRequestDto dto = objectMapper.readValue(rawJson, BankRequestDto.class);

	    BankResponse response =
	            headOfficeRemittanceService.remitAutomation(dto, rawJson, signature);

	    return ResponseEntity.ok(response);
	}


}
