package com.discussion.csye6220.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	
	@GetMapping("/health") 
	public ResponseEntity<?> getHealthEndPoint () {
		System.out.println("******* IN HEALTH CHECK ENDPOINT *******");
		return ResponseEntity.ok().body(null);
	}

}
