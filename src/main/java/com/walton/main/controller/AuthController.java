package com.walton.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walton.main.dto.AuthResponse;
import com.walton.main.dto.LoginRequest;
import com.walton.main.entity.ApiUsers;
import com.walton.main.service.AuthService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("register")
	public ResponseEntity<AuthResponse> register(@RequestBody ApiUsers request)
	{
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
	{
		return ResponseEntity.ok(authService.authenticate(request));
	}
}
