package com.discussion.csye6220.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.discussion.csye6220.dao.UserDAO;
import com.discussion.csye6220.pojo.User;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	private AuthenticationManager authManager;

	@PostMapping("/user")
	public User registerUser(UserDAO userDAO,@Valid @RequestBody User user) {
		return userDAO.create(user);
	}
	
	@GetMapping("/user/{userId}")
	public User getUserById(UserDAO userDAO,@PathVariable long userId)
	{
		return userDAO.getUserById(userId);
	}
	
	@GetMapping("/me")
	public User getLoggedInUser(UserDAO userDAO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		return userDAO.getUserByEmail(userDetails.getUsername()).orElseThrow();
	}
	
	@PutMapping("/user/{userId}")
	public User updateUser(UserDAO userDAO, @PathVariable long userId, @RequestBody User userReqBody) {
		User current = userDAO.getUserById(userId);
		current.setFirstName(userReqBody.getFirstName());
		current.setLastName(userReqBody.getLastName());
		current.setEmail(userReqBody.getEmail());
		current.setDescription(userReqBody.getDescription());
		current.setPassword(userReqBody.getPassword());
		return userDAO.updateUser(current);
	}
	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<Object> deleteUser(UserDAO userDAO, @PathVariable long userId) {
		User userToDelete = userDAO.getUserById(userId);
		
		if(userToDelete == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		else if(userDAO.deleteUser(userToDelete)) {
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
