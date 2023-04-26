package com.discussion.csye6220.pojo;

import java.util.List;

public class AnswerComments {
	
	private Answer answer;
	private List<Comment> comments;
	
	
	public AnswerComments(Answer answer, List<Comment> comments) {
		super();
		this.answer = answer;
		this.comments = comments;
	}
	
	public Answer getAnswer() {
		return answer;
	}
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
}
