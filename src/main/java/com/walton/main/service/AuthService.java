package com.walton.main.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.walton.main.dto.AuthResponse;
import com.walton.main.dto.LoginRequest;
import com.walton.main.entity.ApiUsers;
import com.walton.main.entity.Role;
import com.walton.main.entity.Token;
import com.walton.main.exception.CustomException;
import com.walton.main.jwt.JwtService;
import com.walton.main.repository.ApiUserRepository;
import com.walton.main.repository.TokenRepository;

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
	
	public AuthResponse register(ApiUsers request)
	{
		
	    AuthResponse response=new AuthResponse();
		try {
		if(userRepo.findByUsername(request.getUsername()).isPresent())
		{
			throw new CustomException("Opps Sorry!! This user already exists!!");
		}
		
		ApiUsers user=new ApiUsers();
		user.setFullName(request.getFullName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setUsername(request.getUsername());
		user.setRole(Role.valueOf("ADMIN"));
		
		user=userRepo.save(user);
		String jwtToken=jwtService.generateAccessToken(user);

		
		saveUserToken(jwtToken, user);
		response.setStatusCode(200);
		response.setToken(jwtToken);
		response.setMessage("Welcome! Your registration was successful!!");
		}catch (CustomException e) {
			// TODO: handle exception
			response.setStatusCode(400);
			response.setMessage(e.getMessage());
		}catch (Exception e) {
			// TODO: handle exception
			response.setStatusCode(500);
			response.setMessage("Error Saving a User"+e.getMessage());
		}
		
		return response;
	}
	
	@Transactional
	public AuthResponse authenticate(LoginRequest request)
	{
		 AuthResponse response=new AuthResponse();
		
		 try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
				);
		ApiUsers user = userRepo.findByUsername(request.getUsername())
	            .orElseThrow(() -> new CustomException("Invalid username or password!"));
		
		String jwtToken=jwtService.generateAccessToken(user);
		invalidAllUserToken(user);
		saveUserToken(jwtToken, user);
		response.setStatusCode(200);
		response.setToken(jwtToken);
		response.setMessage("User login successfully!!");
		 }catch (AuthenticationException e) {
	        response.setStatusCode(401);
	        response.setMessage("Login failed!Please check your credentials and try again");
		}catch (CustomException e) {
			// TODO: handle exception
			 response.setStatusCode(404);
			 response.setMessage(e.getMessage());
		}catch (Exception e) {
			// TODO: handle exception
			response.setStatusCode(500);
			response.setMessage("Error Logging in " + e.getMessage());
		}
		return response;
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
    
	
	private void saveUserToken(String jwtToken,ApiUsers user)
	{
		Token token=new Token();
	    token.setToken(jwtToken);
		token.setUser(user);
		token.setExpired(false);
        token.setRevoked(false);
		tokenRepo.save(token);
	}
}
