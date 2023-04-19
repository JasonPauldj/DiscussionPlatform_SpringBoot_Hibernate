package com.discussion.csye6220.dao;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.discussion.csye6220.pojo.Category;

@Component
@Validated
public class CategoryDAO extends DAO {
	
	public Category create(Category category)
	{
		begin();
    	getSession().persist(category);
    	commit();
    	close();
    	return category;
	}

}
