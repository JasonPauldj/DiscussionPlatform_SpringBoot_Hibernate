package com.discussion.csye6220.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.discussion.csye6220.pojo.Comment;
import com.discussion.csye6220.pojo.Answer;
import com.discussion.csye6220.pojo.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


@Component
public class CommentDAO extends DAO {
	
	public Comment create(Comment comment)
	{
		begin();
		User user = getSession().get(User.class,comment.getUser().getUserId());
		comment.setUser(user);
		Answer answer = getSession().get(Answer.class, comment.getAnswer().getAnswerId());
		comment.setAnswer(answer);
    	getSession().persist(comment);
    	commit();
    	close();
    	return comment;
	}
	
	public Comment getCommentById(Long id)
	{
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Comment> cr = cb.createQuery(Comment.class);
		Root<Comment> root = cr.from(Comment.class);
		cr.select(root);
		cr.where(cb.equal(root.get("commentId"),id));
    	Query<Comment> query=getSession().createQuery(cr);
    	Comment comment =(Comment)query.getSingleResult();
    	commit();
    	close();
    	return comment;
	}
	
	public List<Comment> getCommentsByUserId(long userId){
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Comment> cr = cb.createQuery(Comment.class);
		Root<Comment> root = cr.from(Comment.class);
		cr.select(root);
		cr.where(cb.equal(root.get("user").get("userId"), userId));
		Query<Comment> query = getSession().createQuery(cr);
		List<Comment> listOfComments = (List<Comment>) query.getResultList();
		commit();
		close();
		return listOfComments;
	}
	
	public List<Comment> getCommentsByAnswerId(long answerId){
		begin();
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Comment> cr = cb.createQuery(Comment.class);
		Root<Comment> root = cr.from(Comment.class);
		cr.select(root);
		cr.where(cb.equal(root.get("answer").get("answerId"), answerId));
		Query<Comment> query = getSession().createQuery(cr);
		List<Comment> listOfComments = (List<Comment>) query.getResultList();
		commit();
		close();
		return listOfComments;
	}
	
	public Comment updateComment(Comment comment)
	{
		begin();
		getSession().merge(comment);
		commit();
		close();
		return comment;	
	}
	
	public boolean deleteComment(Comment comment)
	{
		begin();
		getSession().remove(comment);
		commit();
		close();
		return true;
	}

}
