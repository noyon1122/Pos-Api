package com.pos.main.api.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pos.main.api.dto.AuthResponse;
import com.pos.main.api.dto.LoginRequest;
import com.pos.main.api.entity.ApiUsers;
import com.pos.main.api.entity.Role;
import com.pos.main.api.entity.Token;
import com.pos.main.api.repository.ApiUserRepository;
import com.pos.main.api.repository.TokenRepository;
import com.pos.main.exception.CustomException;
import com.pos.main.jwt.JwtService;

@Service
public class AuthService {


	private final ApiUserRepository userRepo;

	private final TokenRepository tokenRepo;

	private final JwtService jwtService;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	public AuthService(ApiUserRepository userRepo, TokenRepository tokenRepo, JwtService jwtService,
			PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		super();
		this.userRepo = userRepo;
		this.tokenRepo = tokenRepo;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}
	@Transactional
	public AuthResponse register(ApiUsers request)
	{
		
	
		if(userRepo.findByUsername(request.getUsername()).isPresent())
		{
			throw new CustomException("Opps Sorry!! This user already exists!!");
		}
		
		ApiUsers user=new ApiUsers();
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setUsername(request.getUsername());
		user.setRole(Role.BANK_USER);
		
		user=userRepo.save(user);
		String accessToken=jwtService.generateAccessToken(user);
		String refreshToken=jwtService.generateRefreshToken(user);

		
		saveUserToken(accessToken,refreshToken, user);
		return AuthResponse.builder()
		        .statusCode(200)
		        .accessToken(accessToken)
		        .refreshToken(refreshToken)
		        .message("Registration successful!!")
		        .build();
		
	}
	
	@Transactional
	public AuthResponse authenticate(LoginRequest request)
	{
		 
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
				);
		ApiUsers user = userRepo.findByUsername(request.getUsername())
	            .orElseThrow(() -> new CustomException("Invalid username or password!"));
		
		String accessToken=jwtService.generateAccessToken(user);
		String refreshToken=jwtService.generateRefreshToken(user);
		
		invalidAllUserToken(user);
		saveUserToken(accessToken,refreshToken, user);
		return AuthResponse.builder()
		        .statusCode(200)
		        .accessToken(accessToken)
		        .refreshToken(refreshToken)
		        .message("Login successful!!")
		        .build();
	}
	
    private void invalidAllUserToken(ApiUsers user)
    {
    	List<Token> validTokens = tokenRepo.findAllValidTokensByUser(user.getId());
        if (validTokens.isEmpty()) return;
        validTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepo.saveAll(validTokens);
    }
    
	
	private void saveUserToken(String accessToken,String refreshToken,ApiUsers user)
	{
		Token token = Token.builder()
	            .accessToken(accessToken)
	            .refreshToken(refreshToken)
	            .user(user)
	            .expired(false)
	            .revoked(false)
	            .build();
	    tokenRepo.save(token);
	}
	
	
	public AuthResponse refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        
        if (username != null) {
            ApiUsers user = userRepo.findByUsername(username)
                    .orElseThrow(() -> new CustomException("User not found"));

            // Validate the refresh token
            if (jwtService.isValidRefreshToken(refreshToken, user)) {
            	Token tokenRecord = tokenRepo.findByRefreshToken(refreshToken)
                        .filter(t -> !t.isExpired() && !t.isRevoked())
                        .orElseThrow(() -> new CustomException("Refresh token is invalid or revoked"));

                // new access token generate
                String newAccessToken = jwtService.generateAccessToken(user);
                
                tokenRecord.setAccessToken(newAccessToken); 
                tokenRepo.save(tokenRecord); 

                return AuthResponse.builder()
                        .statusCode(200)
                        .accessToken(newAccessToken)
                        .refreshToken(refreshToken)
                        .message("Token refreshed successfully")
                        .build();
            }
        }
        throw new CustomException("Invalid or Expired Refresh Token");
    }
}
