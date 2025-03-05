package com.in28minutes.restfulwebservices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Post {

	@Id
	@GeneratedValue
	private Integer Id;
	
	private String description;
	
	// FetchType.EAGER --> Los datos de User son recuperados al mismo tiempo que los de Post
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private User user;
	
}
