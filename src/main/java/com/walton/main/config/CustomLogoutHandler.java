package com.walton.main.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.walton.main.repository.TokenRepository;

@Component
public class CustomLogoutHandler implements LogoutHandler {

	private final TokenRepository tokenRepo;

	public CustomLogoutHandler(TokenRepository tokenRepo) {
		super();
		this.tokenRepo = tokenRepo;
	}
	
	@Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String jwt = authHeader.substring(7);
        tokenRepo.findByToken(jwt).ifPresent(t -> {
            t.setExpired(true);
            t.setRevoked(true);
            tokenRepo.save(t);
        });
        
        SecurityContextHolder.clearContext();
    }
}
