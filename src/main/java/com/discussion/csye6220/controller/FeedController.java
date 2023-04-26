package com.discussion.csye6220.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.discussion.csye6220.dao.AnswerDAO;
import com.discussion.csye6220.dao.QuestionDAO;
import com.discussion.csye6220.dao.UserDAO;
import com.discussion.csye6220.pojo.Answer;
import com.discussion.csye6220.pojo.Category;
import com.discussion.csye6220.pojo.QuestionAnswers;
import com.discussion.csye6220.pojo.Question;
import com.discussion.csye6220.pojo.User;


@RestController
public class FeedController {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	QuestionDAO questionDAO;
	
	@Autowired
	AnswerDAO answerDAO;
	
	
	@GetMapping("/feed")
	public ResponseEntity<?> fetchFeed() {
		try {
			List<QuestionAnswers> listOfFeedObjects = new ArrayList<QuestionAnswers>();

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			User user = userDAO.getUserByEmail(userDetails.getUsername()).orElseThrow();

			// if interests are passed
			if (!user.getInterests().isEmpty()) {

				for (Category interest : user.getInterests()) {

					List<Question> listOfQuestions = questionDAO.getQuestionsByCategory(interest.getCategoryId());

					for (Question q : listOfQuestions) {
						List<Answer> listOfAnswers = answerDAO.getAnswersByQuestionId(q.getQuestionId());
						listOfFeedObjects.add(new QuestionAnswers(q, listOfAnswers));
					}
				}

				return ResponseEntity.ok(listOfFeedObjects);

			}
			// generate some random feed
			else {

				System.out.println("*** NO LIST OF INTERESTS PASSED ***");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
		}
	}
}
