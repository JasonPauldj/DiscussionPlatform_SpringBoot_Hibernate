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

import com.discussion.csye6220.dao.QuestionDAO;
import com.discussion.csye6220.pojo.Question;

import jakarta.validation.Valid;

@RestController
public class QuestionController {

	@PostMapping("/question")
	public Question registerQuestion(QuestionDAO questionDAO,@Valid @RequestBody Question question) {
		System.out.println(question.hashCode());
		return questionDAO.create(question);
	}
	
	@GetMapping("/question/{questionId}")
	public Question getQuestionById(QuestionDAO questionDAO,@PathVariable long questionId)
	{
		return questionDAO.getQuestionById(questionId);
	}
	
	@GetMapping("/questions/category/{categoryId}")
	public List<Question> getQuestionsByCategory(QuestionDAO questionDAO, @PathVariable long categoryId)
	{
		return questionDAO.getQuestionsByCategory(categoryId);
	}
	
	@GetMapping("/questions/user/{userId}")
	public List<Question> getQuestionsByUserId(QuestionDAO questionDAO, @PathVariable long userId)
	{
		return questionDAO.getQuestionsByUserId(userId);
	}
	
	@PutMapping("/question/{questionId}")
	public Question updateQuestion(QuestionDAO questionDAO, @PathVariable long questionId, @Valid @RequestBody Question queReqBody) {
		Question current = questionDAO.getQuestionById(questionId);
		current.setBody(queReqBody.getBody());
		current.setCategory(queReqBody.getCategory());
		return questionDAO.updateQuestion(current);
	}
	
	@DeleteMapping("/question/{questionId}")
	public ResponseEntity<Object> deleteQuestion(QuestionDAO questionDAO, @PathVariable long questionId) {
		Question questionToDelete = questionDAO.getQuestionById(questionId);
		
		if(questionToDelete == null) {
			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
		}
		else if(questionDAO.deleteQuestion(questionToDelete)) {
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
