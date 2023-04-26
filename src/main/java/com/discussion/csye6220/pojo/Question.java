package com.discussion.csye6220.pojo;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ForeignKey;


@Entity
//@JsonIdentityInfo(
//		   generator = ObjectIdGenerators.PropertyGenerator.class,
//		   property = "questionId")
public class Question {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long questionId;
	
	@NotNull
	@Column(length=500)
	private String body;
	
	@ManyToOne
	@JoinColumn(foreignKey=@ForeignKey(name="fk_que_user_id"))
	//@JsonBackReference
	private User user;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(foreignKey=@ForeignKey(name="fk_que_cat_id"))
	@NotNull
	private Category category;
	
	@OneToMany(mappedBy="question", cascade=CascadeType.ALL, orphanRemoval=true )
	@JsonIgnore
	private Set<Answer> answers;
	
	public Set<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
