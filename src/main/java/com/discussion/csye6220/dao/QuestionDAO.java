package com.discussion.csye6220.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.discussion.csye6220.pojo.Category;
import com.discussion.csye6220.pojo.Question;
import com.discussion.csye6220.pojo.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Component
public class QuestionDAO extends DAO {
	
	public Question create(Question question)
	{
		begin();
		User user = getSession().get(User.class,question.getUser().getUserId());
		//Hibernate.initialize(user.getQuestions());
		question.setUser(user);
		Category category = getSession().get(Category.class, question.getCategory().getCategoryId());
		question.setCategory(category);
    	getSession().persist(question);
    	commit();
    	close();
    	return question;
	}
	
	public Question getQuestionById(Long id)
	{
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Question> cr = cb.createQuery(Question.class);
		Root<Question> root = cr.from(Question.class);
		cr.select(root);
		cr.where(cb.equal(root.get("questionId"),id));
    	Query<Question> query=getSession().createQuery(cr);
    	Question question =(Question)query.getSingleResult();
//    	Hibernate.initialize(question.getUser().getQuestions());
    //	Hibernate.initialize(question.getAnswers());
    	commit();
    	close();
    	return question;
	}
	
	public List<Question> getQuestionsByCategory(long categoryId)
	{
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Question> cr = cb.createQuery(Question.class);
		Root<Question> root = cr.from(Question.class);
		cr.select(root);
		cr.where(cb.equal(root.get("category").get("categoryId"), categoryId));
		Query<Question> query = getSession().createQuery(cr);
		List<Question> listOfQuestion = (List<Question>) query.getResultList();
		commit();
		close();
		return listOfQuestion;
	}
	
	public List<Question> getQuestionsByUserId(long userId){
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Question> cr = cb.createQuery(Question.class);
		Root<Question> root = cr.from(Question.class);
		cr.select(root);
		cr.where(cb.equal(root.get("user").get("userId"), userId));
		Query<Question> query = getSession().createQuery(cr);
		List<Question> listOfQuestion = (List<Question>) query.getResultList();
		commit();
		close();
		return listOfQuestion;
	}
	
	public Question updateQuestion(Question question)
	{
		begin();
		getSession().merge(question);
		commit();
		close();
		return question;	
	}
	
	public boolean deleteQuestion(Question question)
	{
		begin();
		getSession().remove(question);
		commit();
		close();
		return true;
	}
	

}
