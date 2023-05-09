package com.discussion.csye6220.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.discussion.csye6220.Exception.UserException;
import com.discussion.csye6220.dao.UserDAO;
import com.discussion.csye6220.pojo.User;
import com.discussion.csye6220.util.UserUtil;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	UserUtil userUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

//	@PostMapping("/user")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
//		try {
//			if(user.getPassword() == null || user.getPassword().length() == 0) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//			}	
//			User u = userDAO.create(user);
//			return ResponseEntity.ok(u);
//		} catch (UserException ex) {
//			System.out.println(ex.getMessage());
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//		}
//	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable long userId) {
		try {
			User u = userDAO.getUserById(userId);
			return ResponseEntity.ok(u);
		} catch (UserException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> getLoggedInUser(UserDAO userDAO) {
		try {
			User u = userUtil.getLoggedInUser();
			return ResponseEntity.ok(u);
		} catch (NoSuchElementException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
	}

	@PutMapping("/user/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable long userId, @RequestBody User userReqBody) {
		try {
			// Checking if logged in user is the author of question
			User user = userUtil.getLoggedInUser();
			User current = userDAO.getUserById(userId);
			if (user.getUserId() != current.getUserId()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			
			if(userReqBody.getFirstName() == null || userReqBody.getFirstName().trim().length() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			if(userReqBody.getLastName() == null || userReqBody.getLastName().trim().length() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			if(userReqBody.getEmail() == null || userReqBody.getEmail().trim().length() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
					
			userReqBody.setUserId(current.getUserId());
			current.setFirstName(userReqBody.getFirstName());
			current.setLastName(userReqBody.getLastName());
			current.setEmail(userReqBody.getEmail());
			current.setDescription(userReqBody.getDescription());
			current.setInterests(userReqBody.getInterests());
			User update = userDAO.updateUser(current);
			return ResponseEntity.ok(update);
		} catch (UserException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable long userId) {
		try {
			// Checking if logged in user is the author of question
			User user = userUtil.getLoggedInUser();
			User userToDelete = userDAO.getUserById(userId);

			if (user.getUserId() != userToDelete.getUserId()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			if (userDAO.deleteUser(userToDelete)) {
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (UserException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

}
