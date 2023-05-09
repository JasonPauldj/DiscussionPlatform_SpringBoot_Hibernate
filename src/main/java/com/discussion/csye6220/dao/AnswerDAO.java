package com.discussion.csye6220.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.discussion.csye6220.Exception.AnswerException;
import com.discussion.csye6220.pojo.Answer;
import com.discussion.csye6220.pojo.Question;
import com.discussion.csye6220.pojo.User;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.ValidationException;


@Component
public class AnswerDAO extends DAO {
	
	public Answer create(Answer answer) throws AnswerException
	{
		try {
		begin();
		User user = getSession().get(User.class,answer.getUser().getUserId());
		answer.setUser(user);
		Question question = getSession().get(Question.class, answer.getQuestion().getQuestionId());
		answer.setQuestion(question);
    	getSession().persist(answer);
    	commit();
    	close();
    	return answer;
		}
		catch(ValidationException ex) {
			rollback();
			throw new AnswerException("Error when trying to POST Answer " + ex.getMessage());
		}
		catch(HibernateException ex) {
			rollback();
			throw new AnswerException("Error when trying to POST Answer " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new AnswerException("Error Answer Exception " + ex.getMessage());
		}
		
	}
	
	public Answer getAnswerById(Long id) throws AnswerException
	{
		try
		{
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Answer> cr = cb.createQuery(Answer.class);
		Root<Answer> root = cr.from(Answer.class);
		cr.select(root);
		cr.where(cb.equal(root.get("answerId"),id));
    	Query<Answer> query=getSession().createQuery(cr);
    	Answer answer =(Answer)query.getSingleResult();
    	commit();
    	close();
    	return answer;
		}
		catch(NoResultException ex) {
			rollback();
			throw new AnswerException("Error when trying to GET Answer by Id - NO ANSWER EXISTS");
		}
		catch(HibernateException ex) {
			rollback();
			throw new AnswerException("Error when trying to GET Answer by Id " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new AnswerException("Error Answer Exception " + ex.getMessage());
		}
	}
	
	public List<Answer> getAnswersByUserId(long userId) throws AnswerException{
		try {
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Answer> cr = cb.createQuery(Answer.class);
		Root<Answer> root = cr.from(Answer.class);
		cr.select(root);
		cr.where(cb.equal(root.get("user").get("userId"), userId));
		Query<Answer> query = getSession().createQuery(cr);
		List<Answer> listOfAnswers = (List<Answer>) query.getResultList();
		commit();
		close();
		return listOfAnswers;
		}
		catch(HibernateException ex) {
			rollback();
			throw new AnswerException("Error when trying to GET Answers by UserId " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new AnswerException("Error Answer Exception " + ex.getMessage());
		}
	}
	
	public List<Answer> getAnswersByQuestionId(long questionId) throws AnswerException{
		try {
		begin();
		System.out.println("GET ANSWERS BY QUESTION");
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Answer> cr = cb.createQuery(Answer.class);
		Root<Answer> root = cr.from(Answer.class);
		cr.select(root);
		cr.where(cb.equal(root.get("question").get("questionId"), questionId));
		Query<Answer> query = getSession().createQuery(cr);
		List<Answer> listOfAnswers = (List<Answer>) query.getResultList();
		commit();
		close();
		return listOfAnswers;
		}
		catch(HibernateException ex) {
			rollback();
			throw new AnswerException("Error when trying to GET Answers by QuestionId " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new AnswerException("Error Answer Exception " + ex.getMessage());
		}
	}
	
	public Answer updateAnswer(Answer answer) throws AnswerException
	{
		try
		{
		begin();
		getSession().merge(answer);
		commit();
		close();
		return answer;
		}
		catch(ValidationException ex) {
			rollback();
			throw new AnswerException("Error when trying to POST Question " + ex.getMessage());
		}
		catch(HibernateException ex) {
			rollback();
			throw new AnswerException("Error when trying to PUT Answer " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new AnswerException("Error Answer Exception " + ex.getMessage());
		}
	}
	
	public boolean deleteAnswer(Answer answer) throws AnswerException
	{
		try {
		begin();
		getSession().remove(answer);
		commit();
		close();
		return true;
		}
		catch(ValidationException ex) {
			rollback();
			throw new AnswerException("Error when trying to POST Question " + ex.getMessage());
		}
		catch(HibernateException ex) {
			rollback();
			throw new AnswerException("Error when trying to DELETE Answer " + ex.getMessage());
		}
		catch(Exception ex) {
			rollback();
			throw new AnswerException("Error Answer Exception " + ex.getMessage());
		}
	}

}
