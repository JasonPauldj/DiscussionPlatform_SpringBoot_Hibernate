package com.discussion.csye6220.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.discussion.csye6220.dao.UserDAO;

@Configuration
public class AuthConfig {
	
	@Autowired
	private UserDAO userDAO;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return (userEmail) -> userDAO.getUserByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(userDetailsService());
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager() ;
	}
}
