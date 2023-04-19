package com.discussion.csye6220.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.discussion.csye6220.dao.CommentDAO;
import com.discussion.csye6220.pojo.Comment;

import jakarta.validation.Valid;

@RestController
public class CommentController {

	@PostMapping("/comment")
	public Comment registerQuestion(CommentDAO commentDAO,@Valid @RequestBody Comment comment) {
		return commentDAO.create(comment);
	}
	
	@GetMapping("/comments/user/{userId}")
	public List<Comment> getCommentsByUserId(CommentDAO commentDAO, @PathVariable long userId)
	{
		return commentDAO.getCommentsByUserId(userId);
	}
	
	@GetMapping("/comments/answer/{answerId}")
	public List<Comment> getCommentsByAnswerId(CommentDAO commentDAO, @PathVariable long answerId)
	{
		return commentDAO.getCommentsByAnswerId(answerId);
	}
	
	@PutMapping("/comment/{commentId}")
	public Comment updateComment(CommentDAO commentDAO, @PathVariable long commentId, @Valid @RequestBody Comment commentReqBody) {
		Comment current = commentDAO.getCommentById(commentId);
		current.setBody(commentReqBody.getBody());
		return commentDAO.updateComment(current);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<Object> deleteComment(CommentDAO commentDAO, @PathVariable long commentId) {
		Comment commentToDelete = commentDAO.getCommentById(commentId);
		
		if(commentToDelete == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		else if(commentDAO.deleteComment(commentToDelete)) {
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
