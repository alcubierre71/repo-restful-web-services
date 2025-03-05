package com.in28minutes.restfulwebservices.helloworld;

import lombok.Data;

//@Data
public class HelloWorldBean {

	private String message;
	
	public HelloWorldBean(String message) {
		// TODO Auto-generated constructor stub
		this.message = message; 
	}

	@Override
	public String toString() {
		return "HelloWorldBean [message=" + message + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
