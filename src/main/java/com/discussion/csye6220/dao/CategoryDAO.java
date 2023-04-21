package com.discussion.csye6220.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.discussion.csye6220.pojo.Answer;
import com.discussion.csye6220.pojo.Category;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
	
	public List<Category> getAllCategories(){
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Category> cr = cb.createQuery(Category.class);
		Root<Category> root = cr.from(Category.class);
		cr.select(root);
		Query<Category> query = getSession().createQuery(cr);
		List<Category> listOfCategories = (List<Category>) query.getResultList();
		commit();
		close();
		return listOfCategories;

	}

}
