package com.discussion.csye6220.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.discussion.csye6220.Exception.UserException;
import com.discussion.csye6220.dao.UserDAO;

@RestController
//@CrossOrigin(origins={ "http://localhost:3000"})
public class AuthenticationController {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authManager;

	@PostMapping("/auth/register")
	public ResponseEntity<AuthenticationResponse> registerUser(@Valid @RequestBody User user) {
		try {
			
			if(user.getPassword() == null || user.getPassword().length() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}			
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRole(Role.USER);
			userDAO.create(user);
			String token = jwtUtil.generateToken(user);
			return ResponseEntity.ok(new AuthenticationResponse(token));
		} catch (UserException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/auth/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		User user;
		user = userDAO.getUserByEmail(request.getEmail()).orElseThrow();
		String token = jwtUtil.generateToken(user);
		return ResponseEntity.ok(new AuthenticationResponse(token));

	}
}
