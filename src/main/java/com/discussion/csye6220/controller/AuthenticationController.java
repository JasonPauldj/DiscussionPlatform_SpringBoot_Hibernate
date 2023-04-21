package com.discussion.csye6220.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.discussion.csye6220.pojo.AuthenticationResponse;
import com.discussion.csye6220.pojo.AuthenticationRequest;

import com.discussion.csye6220.pojo.Role;
import com.discussion.csye6220.pojo.User;
import com.discussion.csye6220.security.JwtUtil;

import jakarta.validation.Valid;

import com.discussion.csye6220.dao.UserDAO;

@RestController
//@CrossOrigin(origins={ "http://localhost:3000"})
public class AuthenticationController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/auth/register")
	public ResponseEntity<AuthenticationResponse> registerUser(UserDAO userDAO,@Valid @RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.USER);
		userDAO.create(user);
		String token = jwtUtil.generateToken(user);
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	
	@PostMapping("/auth/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(UserDAO userDAO, @RequestBody AuthenticationRequest request)
	{
		System.out.println("*** INSIDE authenticate method of AuthenticationController  ***");
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		User user = userDAO.getUserByEmail(request.getEmail()).orElseThrow();
		
		String token = jwtUtil.generateToken(user);
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
}
