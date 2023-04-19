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

import com.discussion.csye6220.dao.AnswerDAO;
import com.discussion.csye6220.dao.QuestionDAO;
import com.discussion.csye6220.pojo.Answer;
import com.discussion.csye6220.pojo.Question;

import jakarta.validation.Valid;

@RestController
public class AnswerController {

	@PostMapping("/answer")
	public Answer registerAnswer(AnswerDAO answerDAO,@Valid @RequestBody Answer answer) {
		return answerDAO.create(answer);
	}
	
	@GetMapping("/answers/user/{userId}")
	public List<Answer> getAnswersByUserId(AnswerDAO answerDAO, @PathVariable long userId)
	{
		return answerDAO.getAnswersByUserId(userId);
	}
	
	@GetMapping("/answers/answer/{answerId}")
	public List<Answer> getAnswersByQuestionId(AnswerDAO answerDAO, @PathVariable long questionId)
	{
		return answerDAO.getAnswersByQuestionId(questionId);
	}
	
	@PutMapping("/answer/{answerId}")
	public Answer updateAnswer(AnswerDAO answerDAO, @PathVariable long answerId, @Valid @RequestBody Answer ansReqBody) {
		Answer current = answerDAO.getAnswerById(answerId);
		current.setBody(ansReqBody.getBody());
		return answerDAO.updateAnswer(current);
	}
	
	@DeleteMapping("/answer/{answerId}")
	public ResponseEntity<Object> deleteAnswer(AnswerDAO answerDAO, @PathVariable long answerId) {
		Answer answerToDelete = answerDAO.getAnswerById(answerId);
		
		if(answerToDelete == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		else if(answerDAO.deleteAnswer(answerToDelete)) {
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
