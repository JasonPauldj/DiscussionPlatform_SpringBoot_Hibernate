package com.discussion.csye6220.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.discussion.csye6220.dao.AnswerDAO;
import com.discussion.csye6220.dao.CommentDAO;
import com.discussion.csye6220.pojo.Answer;
import com.discussion.csye6220.pojo.AnswerComments;
import com.discussion.csye6220.pojo.Comment;


import java.util.*;

@RestController
public class AnswerCommentsController {
	
	@Autowired
	AnswerDAO answerDAO;
	
	@Autowired
	CommentDAO commentDAO; 
	
	@GetMapping("/answerComments/{answerId}")
	public ResponseEntity<?> getAnswerComments(@PathVariable long answerId){
	 try {
		Answer answer;
		answer = answerDAO.getAnswerById(answerId);

		List<Comment> comments = commentDAO.getCommentsByAnswerId(answerId);
		
		AnswerComments aComm = new AnswerComments(answer,comments);
		
		return ResponseEntity.ok(aComm);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
