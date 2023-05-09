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

import com.discussion.csye6220.Exception.QuestionException;
import com.discussion.csye6220.dao.QuestionDAO;
import com.discussion.csye6220.pojo.Question;
import com.discussion.csye6220.pojo.User;
import com.discussion.csye6220.util.UserUtil;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

@RestController
public class QuestionController {

	@Autowired
	QuestionDAO questionDAO;

	@Autowired
	UserUtil userUtil;

	@PostMapping("/question")
	public ResponseEntity<?> registerQuestion(@Valid @RequestBody Question question) {
		try {
			User user = userUtil.getLoggedInUser();
			question.setUser(user);
			
			if(question.getBody() == null || question.getBody().trim().length() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			Question q = questionDAO.create(question);
			return ResponseEntity.ok(q);
		} catch (QuestionException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@GetMapping("/question/{questionId}")
	public ResponseEntity<?> getQuestionById(@PathVariable long questionId) {
		try {
			Question q = questionDAO.getQuestionById(questionId);
			return ResponseEntity.ok(q);
		} catch (NoResultException ex) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		} catch (QuestionException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@GetMapping("/questions/category/{categoryId}")
	public ResponseEntity<?> getQuestionsByCategory(@PathVariable long categoryId) {
		try {
			List<Question> questions = questionDAO.getQuestionsByCategory(categoryId);
			return ResponseEntity.ok(questions);
		} catch (QuestionException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@GetMapping("/questions/user/{userId}")
	public ResponseEntity<?> getQuestionsByUserId(@PathVariable long userId) {
		try {
			List<Question> questions = questionDAO.getQuestionsByUserId(userId);
			return ResponseEntity.ok(questions);
		} catch (QuestionException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PatchMapping("/question/{questionId}")
	public ResponseEntity<?> updateQuestion(@PathVariable long questionId, @Valid @RequestBody Question queReqBody) {
		try {

			// Checking if logged in user is the author of question
			User user = userUtil.getLoggedInUser();
			Question current = questionDAO.getQuestionById(questionId);
			
			if (user.getUserId() != current.getUser().getUserId()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			
			if(queReqBody.getBody() == null || queReqBody.getBody().trim().length() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			current.setBody(queReqBody.getBody());
			current.setCategory(queReqBody.getCategory());
			Question update = questionDAO.updateQuestion(current);
			return ResponseEntity.ok(update);
		} catch (QuestionException ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/question/{questionId}")
	public ResponseEntity<Object> deleteQuestion(@PathVariable long questionId) {
		try {

			// Checking if logged in user is the author of question
			User user = userUtil.getLoggedInUser();
			Question questionToDelete = questionDAO.getQuestionById(questionId);

			if (user.getUserId() != questionToDelete.getUser().getUserId()) {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			if (questionDAO.deleteQuestion(questionToDelete)) {
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (NoResultException ex) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		} catch (QuestionException ex) {
			System.out.println(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
