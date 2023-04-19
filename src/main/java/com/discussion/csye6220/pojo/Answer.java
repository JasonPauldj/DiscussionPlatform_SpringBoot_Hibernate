package com.discussion.csye6220.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.NotNull;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;


@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long answerId;
	
	@NotNull
	private String body;
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_ans_user_id"))
	private User user;
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_question_id"))
	private Question question;
	
	@OneToMany(mappedBy="answer", cascade=CascadeType.ALL, orphanRemoval=true)
	@JsonIgnore
	private Set<Comment> comments;
	
	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	
	
}
