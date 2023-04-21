package com.discussion.csye6220.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.discussion.csye6220.dao.CategoryDAO;
import com.discussion.csye6220.pojo.Category;

import jakarta.validation.Valid;

@RestController
public class CategoryController {

	@PostMapping("/category")
	public Category registerCategory(CategoryDAO categoryDAO,@Valid @RequestBody Category category) {
		return categoryDAO.create(category);
	}
	
	@GetMapping("/categories")
	public List<Category> getAllCategories(CategoryDAO categoryDAO){
		return categoryDAO.getAllCategories();
	}
}
