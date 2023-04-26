package com.discussion.csye6220.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.discussion.csye6220.dao.AnswerDAO;
import com.discussion.csye6220.dao.QuestionDAO;
import com.discussion.csye6220.pojo.Answer;
import com.discussion.csye6220.pojo.Question;
import com.discussion.csye6220.pojo.QuestionAnswers;

import java.util.*;

@RestController
public class QuestionAnswersController {
	
	@Autowired
	QuestionDAO questionDAO;
	
	@Autowired
	AnswerDAO answerDAO; 
	
	@GetMapping("/questionAnswers/{questionId}")
	public ResponseEntity<?> getQuestionAnswers(@PathVariable long questionId){
	 try {
		Question question;
		question = questionDAO.getQuestionById(questionId);

		List<Answer> answers = answerDAO.getAnswersByQuestionId(questionId);
		
		QuestionAnswers qAns = new QuestionAnswers(question,answers);
		
		return ResponseEntity.ok(qAns);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
