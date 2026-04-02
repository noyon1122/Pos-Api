package com.pos.main.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pos.main.api.service.MyUserDetailsService;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final MyUserDetailsService userDetailsService;
	
	public JwtFilter(JwtService jwtService, MyUserDetailsService userDetailsService) {
		super();
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authHeader=request.getHeader("Authorization");
		
		if(authHeader ==null || !authHeader.startsWith("Bearer "))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		String token=authHeader.substring(7);
		String username=jwtService.extractUsername(token);
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=userDetailsService.loadUserByUsername(username);
			
			if(jwtService.isValid(token, userDetails))
			{
				UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				
				authenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
						);
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}
}
