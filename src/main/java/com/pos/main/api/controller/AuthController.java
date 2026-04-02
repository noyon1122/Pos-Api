package com.pos.main.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.main.api.dto.AuthResponse;
import com.pos.main.api.dto.LoginRequest;
import com.pos.main.api.entity.ApiUsers;
import com.pos.main.api.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth/")
@Tag(name = "Authentication API",description = "Register and Login")
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
	
	
	@PostMapping("refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String refreshToken = authHeader.substring(7);
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }
}
