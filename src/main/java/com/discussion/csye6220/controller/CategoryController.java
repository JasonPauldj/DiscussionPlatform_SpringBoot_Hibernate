package com.discussion.csye6220.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.discussion.csye6220.Exception.CategoryException;
import com.discussion.csye6220.dao.CategoryDAO;
import com.discussion.csye6220.pojo.Category;
import com.discussion.csye6220.pojo.Role;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/category")
	public ResponseEntity<?> registerCategory( @Valid @RequestBody Category category) {
		try {
			Category c = categoryDAO.create(category);
			return ResponseEntity.ok(c);
		} catch (CategoryException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/categories")
	public ResponseEntity<?> getAllCategories() {
		try {
			List<Category> categories = categoryDAO.getAllCategories();
			return ResponseEntity.ok(categories);
		} catch (CategoryException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
}
