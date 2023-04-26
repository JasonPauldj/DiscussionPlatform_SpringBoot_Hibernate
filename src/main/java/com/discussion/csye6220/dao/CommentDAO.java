package com.discussion.csye6220.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.discussion.csye6220.pojo.Comment;
import com.discussion.csye6220.Exception.CategoryException;
import com.discussion.csye6220.Exception.CommentException;
import com.discussion.csye6220.pojo.Answer;
import com.discussion.csye6220.pojo.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Component
public class CommentDAO extends DAO {

	public Comment create(Comment comment) throws CommentException {
		try {
			begin();
			User user = getSession().get(User.class, comment.getUser().getUserId());
			comment.setUser(user);
			Answer answer = getSession().get(Answer.class, comment.getAnswer().getAnswerId());
			comment.setAnswer(answer);
			getSession().persist(comment);
			commit();
			close();
			return comment;
		} catch (HibernateException ex) {
			rollback();
			throw new CommentException("Error when trying to POST Comment " + ex.getMessage());
		} catch (Exception ex) {
			rollback();
			throw new CommentException("Error Comment Exception " + ex.getMessage());
		}
	}

	public Comment getCommentById(Long id) throws CommentException {
		try {
			begin();
			CriteriaBuilder cb = getSession().getCriteriaBuilder();
			CriteriaQuery<Comment> cr = cb.createQuery(Comment.class);
			Root<Comment> root = cr.from(Comment.class);
			cr.select(root);
			cr.where(cb.equal(root.get("commentId"), id));
			Query<Comment> query = getSession().createQuery(cr);
			Comment comment = (Comment) query.getSingleResult();
			commit();
			close();
			return comment;
		} catch (HibernateException ex) {
			rollback();
			throw new CommentException("Error when trying to GET Comment By Id " + ex.getMessage());
		} catch (Exception ex) {
			rollback();
			throw new CommentException("Error Comment Exception " + ex.getMessage());
		}
	}

	public List<Comment> getCommentsByUserId(long userId) throws CommentException {
		try {
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
		} catch (HibernateException ex) {
			rollback();
			throw new CommentException("Error when trying to GET Comments By User Id " + ex.getMessage());
		} catch (Exception ex) {
			rollback();
			throw new CommentException("Error Comment Exception " + ex.getMessage());
		}
	}

	public List<Comment> getCommentsByAnswerId(long answerId) throws CommentException {
		try {
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
		} catch (HibernateException ex) {
			rollback();
			throw new CommentException("Error when trying to GET Comments By Answer Id " + ex.getMessage());
		} catch (Exception ex) {
			rollback();
			throw new CommentException("Error Comment Exception " + ex.getMessage());
		}
	}

	public Comment updateComment(Comment comment) throws CommentException {
		try {
			begin();
			getSession().merge(comment);
			commit();
			close();
			return comment;
		} catch (HibernateException ex) {
			rollback();
			throw new CommentException("Error when trying to PUT Comment " + ex.getMessage());
		} catch (Exception ex) {
			rollback();
			throw new CommentException("Error Comment Exception " + ex.getMessage());
		}
	}

	public boolean deleteComment(Comment comment) throws CommentException {
		try {
			begin();
			getSession().remove(comment);
			commit();
			close();
			return true;
		} catch (HibernateException ex) {
			rollback();
			throw new CommentException("Error when trying to DELETE COMMENT " + ex.getMessage());
		} catch (Exception ex) {
			rollback();
			throw new CommentException("Error Comment Exception " + ex.getMessage());
		}
	}
}
