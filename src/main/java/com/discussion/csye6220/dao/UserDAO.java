package com.discussion.csye6220.dao;

import java.util.Optional;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.discussion.csye6220.Exception.CommentException;
import com.discussion.csye6220.Exception.UserException;
import com.discussion.csye6220.pojo.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Component
public class UserDAO extends DAO {

	public User create(User user) throws UserException {
		try {
			begin();
			getSession().persist(user);
			commit();
			close();
			return user;
		} catch (HibernateException ex) {
			rollback();
			throw new UserException("Error when trying to POST User " + ex.getMessage());
		} catch (Exception ex) {
			rollback();
			throw new UserException("Error User Exception " + ex.getMessage());
		}
	}

	public User getUserById(Long id) throws UserException {
		try {
			begin();
			CriteriaBuilder cb = getSession().getCriteriaBuilder();
			CriteriaQuery<User> cr = cb.createQuery(User.class);
			Root<User> root = cr.from(User.class);
			cr.select(root);
			cr.where(cb.equal(root.get("userId"), id));
			Query<User> query = getSession().createQuery(cr);
			User user = (User) query.getSingleResult();
			Hibernate.initialize(user.getQuestions());
			Hibernate.initialize(user.getRole());
			commit();
			close();
			return user;
		} catch (HibernateException ex) {
			rollback();
			throw new UserException("Error when trying to GET User by ID " + ex.getMessage());
		} catch (Exception ex) {
			rollback();
			throw new UserException("Error User Exception " + ex.getMessage());
		}

	}

	public Optional<User> getUserByEmail(String email){
		try {
			begin();
			System.out.println("GET USER BY EMAIL");
			CriteriaBuilder cb = getSession().getCriteriaBuilder();
			CriteriaQuery<User> cr = cb.createQuery(User.class);
			Root<User> root = cr.from(User.class);
			cr.select(root);
			cr.where(cb.equal(root.get("email"), email));
			Query<User> query = getSession().createQuery(cr);
			User user = (User) query.getSingleResult();
			commit();
			close();
			return Optional.ofNullable(user);
		} catch (HibernateException ex) {
			rollback();
			return Optional.ofNullable(null);
		} catch (Exception ex) {
			rollback();
			return Optional.ofNullable(null);
		}
	}

	public User updateUser(User user) throws UserException {
		try {
			begin();
			getSession().merge(user);
			commit();
			close();
			return user;
		} catch (HibernateException ex) {
			rollback();
			throw new UserException("Error when trying to PUT User " + ex.getMessage());
		} catch (Exception ex) {
			rollback();
			throw new UserException("Error User Exception " + ex.getMessage());
		}
	}

	public boolean deleteUser(User user) throws UserException {
		try {
			begin();
			getSession().remove(user);
			commit();
			close();
			return true;
		} catch (HibernateException ex) {
			rollback();
			throw new UserException("Error when trying to DELETE User " + ex.getMessage());
		} catch (Exception ex) {
			rollback();
			throw new UserException("Error User Exception " + ex.getMessage());
		}
	}

}
