package com.discussion.csye6220;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.discussion.csye6220.dao.CategoryDAO;
import com.discussion.csye6220.pojo.Category;

@SpringBootApplication
public class DiscussionPlatformApplication {

	private static CategoryDAO categoryDAO = new CategoryDAO();

	public static void main(String[] args) {
		SpringApplication.run(DiscussionPlatformApplication.class, args);
		
		//prepopulate categories is used only for dev purposes. For Prod purpose we can remove this
		try {
			if (categoryDAO.getAllCategories() == null || categoryDAO.getAllCategories().isEmpty()) {
				prepopulateCategories();
			}
			else {
				System.out.println("Categories already exist. Needn't prepopulate");
			}
		} catch (Exception ex) {
			System.out.println("Problem prepopulating the categories");
		}

	}

	public static void prepopulateCategories() {
		String[] categories = { "Sports", "DevOps" };
		try {
			for (String category : categories) {
				Category c = new Category();
				c.setCategory(category);
				categoryDAO.create(c);
			}
		} catch (Exception ex) {
			System.out.println("Problem prepopulating the categories");
		}

	}

}
