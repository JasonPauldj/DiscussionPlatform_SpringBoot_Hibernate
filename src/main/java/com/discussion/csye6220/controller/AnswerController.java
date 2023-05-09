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

import com.discussion.csye6220.Exception.AnswerException;
import com.discussion.csye6220.dao.AnswerDAO;
import com.discussion.csye6220.pojo.Answer;
import com.discussion.csye6220.pojo.User;
import com.discussion.csye6220.util.UserUtil;

import jakarta.validation.Valid;

@RestController
public class AnswerController {

	@Autowired
	AnswerDAO answerDAO;

	@Autowired
	UserUtil userUtil;

	@PostMapping("/answer")
	public ResponseEntity<?> registerAnswer(@Valid @RequestBody Answer answer) {
		try {
			User user = userUtil.getLoggedInUser();
			answer.setUser(user);
			
			if(answer.getBody() == null  || answer.getBody().trim().length() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			Answer a = answerDAO.create(answer);
			return ResponseEntity.ok(a);
		} catch (AnswerException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/answers/user/{userId}")
	public ResponseEntity<?> getAnswersByUserId(@PathVariable long userId) {
		try {
			List<Answer> answers = answerDAO.getAnswersByUserId(userId);
			return ResponseEntity.ok(answers);
		} catch (AnswerException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/answers/answer/{answerId}")
	public ResponseEntity<?> getAnswersByQuestionId(@PathVariable long questionId) {
		try {
			List<Answer> answers = answerDAO.getAnswersByQuestionId(questionId);
			return ResponseEntity.ok(answers);
		} catch (AnswerException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@PatchMapping("/answer/{answerId}")
	public ResponseEntity<?> updateAnswer(@PathVariable long answerId, @Valid @RequestBody Answer ansReqBody) {
		try {

			// Checking if logged in user is the author of answer
			User user = userUtil.getLoggedInUser();
			Answer current = answerDAO.getAnswerById(answerId);
			
			if (user.getUserId() != current.getUser().getUserId()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			
			if(ansReqBody.getBody() == null  || ansReqBody.getBody().trim().length() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			current.setBody(ansReqBody.getBody());
			Answer update = answerDAO.updateAnswer(current);
			return ResponseEntity.ok(update);
		} catch (AnswerException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@DeleteMapping("/answer/{answerId}")
	public ResponseEntity<Object> deleteAnswer(@PathVariable long answerId) {
		try {

			// Checking if logged in user is the author of answer
			User user = userUtil.getLoggedInUser();
			Answer answerToDelete = answerDAO.getAnswerById(answerId);

			if (user.getUserId() != answerToDelete.getUser().getUserId()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			if (answerDAO.deleteAnswer(answerToDelete)) {
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (AnswerException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
}
