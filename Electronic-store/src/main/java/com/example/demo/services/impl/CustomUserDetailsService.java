package com.example.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Users;
import com.example.demo.repositories.UsersRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UsersRepo userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//loading user from db using user repo
		Users user = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("user with given email not found!!"));
		return user;
	}

}
