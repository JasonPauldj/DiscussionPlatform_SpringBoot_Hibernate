package com.discussion.csye6220.dao;

import java.util.Optional;

import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.discussion.csye6220.pojo.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


@Component
@Validated
public class UserDAO extends DAO {
	
	public User create(User user)
	{
		begin();
    	getSession().persist(user);
    	commit();
    	close();
    	return user;
	}
	
	public User getUserById(Long id)
	{
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);
		cr.select(root);
		cr.where(cb.equal(root.get("userId"),id));
    	Query<User> query=getSession().createQuery(cr);
    	User user =(User)query.getSingleResult();
    	Hibernate.initialize(user.getQuestions());
    	Hibernate.initialize(user.getRole());
    	commit();
    	close();
    	return user;
	}
	
	public Optional<User> getUserByEmail(String email)
	{
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);
		cr.select(root);
		cr.where(cb.equal(root.get("email"),email));
    	Query<User> query=getSession().createQuery(cr);
    	User user =(User)query.getSingleResult();
    	commit();
    	close();
    	return Optional.ofNullable(user);
	}
	
	public User updateUser(User user)
	{
		begin();
		getSession().merge(user);
		commit();
		close();
		return user;	
	}
	
	public boolean deleteUser(User user)
	{
		begin();
		getSession().remove(user);
		commit();
		close();
		return true;
	}

}
