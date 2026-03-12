package com.walton.main.jwt;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.walton.main.entity.ApiUsers;
import com.walton.main.repository.TokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	
	private final TokenRepository tokenRepo;
	
	
	 public JwtService(TokenRepository tokenRepo) {
		super();
		this.tokenRepo = tokenRepo;
	}
	 
	    @Value("${application.security.jwt.secret-key}")
	    private String secretKey;

	    @Value("${application.security.jwt.expiration}")
	    private long tokenExpire;
	
	public Claims extractAllClaims(String token)
	{
		return Jwts
				.parser()
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public <T> T extractClaim(String token,Function<Claims, T> resolver)
	{
		Claims claims=extractAllClaims(token);
		return resolver.apply(claims);
	}
	
	public String extractUsername(String token)
	{
		return extractClaim(token, Claims::getSubject);
	}
	
	public boolean isTokenExpired(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token)
	{
		return extractClaim(token, Claims::getExpiration);
	}
	
	public boolean isValid(String token,UserDetails user)
	{
		String username=extractUsername(token);
		
		boolean validToken=tokenRepo.findByToken(token).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
		
		return (username.equals(user.getUsername()) && !isTokenExpired(token) && validToken);
	}
	
	//generate token
	
	private String generateToken(ApiUsers user,long expiredTime)
	{
		return Jwts
				.builder()
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+expiredTime))
				.signWith(getSigninKey())
				.compact();
	}
	
   public String generateAccessToken(ApiUsers user)
   {
	   return generateToken(user, tokenExpire);
   }
	
	
	private SecretKey getSigninKey()
	{
		byte [] keyBytes=Decoders.BASE64URL.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
