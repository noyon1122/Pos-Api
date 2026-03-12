package com.walton.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.walton.main.jwt.JwtFilter;
import com.walton.main.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	private final MyUserDetailsService userDetailsService;
	private final JwtFilter jwtFilter;
	private final CustomLogoutHandler logoutHandler;
	

	public SecurityConfig(MyUserDetailsService userDetailsService, JwtFilter jwtFilter,
			CustomLogoutHandler logoutHandler) {
		super();
		this.userDetailsService = userDetailsService;
		this.jwtFilter = jwtFilter;
		this.logoutHandler = logoutHandler;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService); 
	    authProvider.setPasswordEncoder(passwordEncoder()); 
	    return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeRequests(auth -> auth
	            .antMatchers("/api/auth/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authenticationProvider(authenticationProvider()) 
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	        .logout(logout -> logout
	                .logoutUrl("/api/auth/logout")
	                .addLogoutHandler(logoutHandler)
	                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
	            );

	    return http.build();
	}
	

	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
