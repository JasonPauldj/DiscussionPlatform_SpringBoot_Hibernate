package com.discussion.csye6220.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.discussion.csye6220.Exception.CategoryException;
import com.discussion.csye6220.Exception.QuestionException;
import com.discussion.csye6220.pojo.Category;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Component
@Validated
public class CategoryDAO extends DAO {


	public Category create(Category category) throws CategoryException {
		try {
			begin();
			getSession().persist(category);
			commit();
			close();
			return category;
		} catch (HibernateException ex) {
			rollback();
			throw new CategoryException("Error when trying to POST Category " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new CategoryException("Error Category Exception " + ex.getMessage()); 
		}
	}

	public List<Category> getAllCategories() throws CategoryException {
		try {
			begin();
			System.out.println("GET ALL CATEGORIES");
			CriteriaBuilder cb = getSession().getCriteriaBuilder();
			CriteriaQuery<Category> cr = cb.createQuery(Category.class);
			Root<Category> root = cr.from(Category.class);
			cr.select(root);
			Query<Category> query = getSession().createQuery(cr);
			List<Category> listOfCategories = (List<Category>) query.getResultList();
			commit();
			close();
			return listOfCategories;
		} catch (HibernateException ex) {
			rollback();
			throw new CategoryException("Error when trying to GET All Categories " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new CategoryException("Error Question Exception " + ex.getMessage()); 
		}

	}

}
