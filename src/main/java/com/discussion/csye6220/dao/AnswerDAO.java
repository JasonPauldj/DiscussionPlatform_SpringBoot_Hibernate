package com.discussion.csye6220.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.discussion.csye6220.pojo.Answer;
import com.discussion.csye6220.pojo.Question;
import com.discussion.csye6220.pojo.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


@Component
public class AnswerDAO extends DAO {
	
	public Answer create(Answer answer)
	{
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
	
	public Answer getAnswerById(Long id)
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
	
	public List<Answer> getAnswersByUserId(long userId){
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
	
	public List<Answer> getAnswersByQuestionId(long questionId){
		begin();
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
	
	public Answer updateAnswer(Answer answer)
	{
		begin();
		getSession().merge(answer);
		commit();
		close();
		return answer;	
	}
	
	public boolean deleteAnswer(Answer answer)
	{
		begin();
		getSession().remove(answer);
		commit();
		close();
		return true;
	}

}
