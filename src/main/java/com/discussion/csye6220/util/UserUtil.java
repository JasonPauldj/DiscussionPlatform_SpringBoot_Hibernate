package com.discussion.csye6220.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.discussion.csye6220.dao.UserDAO;
import com.discussion.csye6220.pojo.User;

@Component
public class UserUtil {
	
	@Autowired
	private UserDAO userDAO;


	public User getLoggedInUser() {
		System.out.println("IN LOGGED IN USER " + this);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		System.out.println("*** USER *** " + userDetails.getUsername());
		User u = userDAO.getUserByEmail(userDetails.getUsername()).orElseThrow();
		
		return u;
	}
}
