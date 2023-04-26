package com.discussion.csye6220.pojo;

import java.util.List;

public class QuestionAnswers {
	
	private Question question;
	private List<Answer> answers;
	
	
	public QuestionAnswers(Question question, List<Answer> answers) {
		super();
		this.question = question;
		this.answers = answers;
	}
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	
}
