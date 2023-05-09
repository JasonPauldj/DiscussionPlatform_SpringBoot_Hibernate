package com.discussion.csye6220.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.discussion.csye6220.Exception.QuestionException;
import com.discussion.csye6220.pojo.Category;
import com.discussion.csye6220.pojo.Question;
import com.discussion.csye6220.pojo.User;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ValidationException;

@Component
public class QuestionDAO extends DAO {
	
	public Question create(Question question) throws QuestionException
	{
		try {		
		begin();
		User user = getSession().get(User.class,question.getUser().getUserId());
		question.setUser(user);
		Category category = getSession().get(Category.class, question.getCategory().getCategoryId());
		question.setCategory(category);
    	getSession().persist(question);
    	commit();
    	close();
    	return question;
		}
		catch(ValidationException ex) {
			rollback();
			throw new QuestionException("Error when trying to POST Question " + ex.getMessage());
		}
		catch(HibernateException ex) {
			rollback();
			throw new QuestionException("Error when trying to POST Question " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new QuestionException("Error Question Exception " + ex.getMessage()); 
		}
	}
	
	public Question getQuestionById(Long id) throws QuestionException 
	{
		try {
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Question> cr = cb.createQuery(Question.class);
		Root<Question> root = cr.from(Question.class);
		cr.select(root);
		cr.where(cb.equal(root.get("questionId"),id));
    	Query<Question> query=getSession().createQuery(cr);
    	Question question =(Question)query.getSingleResult();
    	commit();
    	close();
    	return question;
		}
		catch(NoResultException ex) {
			rollback();
			throw new QuestionException("Error when trying to GET Question by Id - NO QUESTION EXISTS");
		}
		catch(ValidationException ex) {
			rollback();
			throw new QuestionException("Error when trying to POST Question " + ex.getMessage());
		}
		catch(HibernateException ex) {
			rollback();
			throw new QuestionException("Error when trying to GET Question by Id " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new QuestionException("Error Question Exception " + ex.getMessage()); 
		}
		
	}
	
	public List<Question> getQuestionsByCategory(long categoryId) throws QuestionException
	{
		try
		{
		begin();
		System.out.println("GET QUESTIONS BY CATEGORY");
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Question> cr = cb.createQuery(Question.class);
		Root<Question> root = cr.from(Question.class);
		cr.select(root);
		cr.where(cb.equal(root.get("category").get("categoryId"), categoryId));
		cr.orderBy(cb.desc(root.get("questionId")));
		Query<Question> query = getSession().createQuery(cr);
		List<Question> listOfQuestion = (List<Question>) query.getResultList();
		commit();
		close();
		return listOfQuestion;
		}
		catch(HibernateException ex) {
			rollback();
			throw new QuestionException("Error when trying to GET Question by CategoryID " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new QuestionException("Error Question Exception " + ex.getMessage()); 
		}
	}
	
	public List<Question> getQuestionsByUserId(long userId) throws QuestionException{
		try
		{
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Question> cr = cb.createQuery(Question.class);
		Root<Question> root = cr.from(Question.class);
		cr.select(root);
		cr.where(cb.equal(root.get("user").get("userId"), userId));
		cr.orderBy(cb.desc(root.get("questionId")));
		Query<Question> query = getSession().createQuery(cr);
		List<Question> listOfQuestion = (List<Question>) query.getResultList();
		commit();
		close();
		return listOfQuestion;
		}
		catch(HibernateException ex) {
			rollback();
			throw new QuestionException("Error when trying to GET Question by UserId " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new QuestionException("Error Question Exception " + ex.getMessage()); 
		}
	}
	
	public Question updateQuestion(Question question) throws QuestionException
	{
		try
		{
		begin();
		getSession().merge(question);
		commit();
		close();
		return question;	
		}
		catch(ValidationException ex) {
			rollback();
			throw new QuestionException("Error when trying to POST Question " + ex.getMessage());
		}
		catch(HibernateException ex) {
			rollback();
			throw new QuestionException("Error when trying to PUT Question " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new QuestionException("Error Question Exception " + ex.getMessage()); 
		}
	}
	
	public boolean deleteQuestion(Question question) throws QuestionException
	{
		try {
		begin();
		getSession().remove(question);
		commit();
		close();
		return true;
		}
		catch(ValidationException ex) {
			rollback();
			throw new QuestionException("Error when trying to POST Question " + ex.getMessage());
		}
		catch(HibernateException ex) {
			rollback();
			throw new QuestionException("Error when trying to DELETE Question " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new QuestionException("Error Question Exception " + ex.getMessage()); 
		}
		
	}
	

}
