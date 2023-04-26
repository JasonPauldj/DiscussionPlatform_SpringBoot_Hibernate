package com.discussion.csye6220.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.discussion.csye6220.Exception.CommentException;
import com.discussion.csye6220.dao.CommentDAO;
import com.discussion.csye6220.pojo.Comment;
import com.discussion.csye6220.pojo.User;
import com.discussion.csye6220.util.UserUtil;

import jakarta.validation.Valid;

@RestController
public class CommentController {

	@Autowired
	CommentDAO commentDAO;

	@Autowired
	UserUtil userUtil;

	@PostMapping("/comment")
	public ResponseEntity<?> registerQuestion(@Valid @RequestBody Comment comment) {
		try {
			Comment c = commentDAO.create(comment);
			return ResponseEntity.ok(c);
		} catch (CommentException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}

	@GetMapping("/comments/user/{userId}")
	public ResponseEntity<?> getCommentsByUserId(@PathVariable long userId) {
		try {
			List<Comment> comments = commentDAO.getCommentsByUserId(userId);
			return ResponseEntity.ok(comments);
		} catch (CommentException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}

	@GetMapping("/comments/answer/{answerId}")
	public ResponseEntity<?> getCommentsByAnswerId(@PathVariable long answerId) {
		try {
			List<Comment> comments = commentDAO.getCommentsByAnswerId(answerId);
			return ResponseEntity.ok(comments);
		} catch (CommentException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}

	@PatchMapping("/comment/{commentId}")
	public ResponseEntity<?> updateComment(@PathVariable long commentId, @Valid @RequestBody Comment commentReqBody) {
		try {

			// Checking if logged in user is the author of comment
			User user = userUtil.getLoggedInUser();
			Comment current = commentDAO.getCommentById(commentId);

			if (user.getUserId() != current.getUser().getUserId()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			current.setBody(commentReqBody.getBody());
			Comment update = commentDAO.updateComment(current);
			return ResponseEntity.ok(update);
		} catch (CommentException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}

	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<Object> deleteComment(@PathVariable long commentId) {
		try {
			// Checking if logged in user is the author of comment
			User user = userUtil.getLoggedInUser();
			Comment commentToDelete = commentDAO.getCommentById(commentId);

			if (user.getUserId() != commentToDelete.getUser().getUserId()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			if (commentDAO.deleteComment(commentToDelete)) {
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (CommentException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}

	}
}
