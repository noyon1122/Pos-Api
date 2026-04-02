package com.pos.main.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pos.main.api.repository.ApiUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private final ApiUserRepository userRepo;
	
	public MyUserDetailsService(ApiUserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		return userRepo.findByUsername(username).orElseThrow(
				()-> new UsernameNotFoundException("User not found")
				);

	}
}
