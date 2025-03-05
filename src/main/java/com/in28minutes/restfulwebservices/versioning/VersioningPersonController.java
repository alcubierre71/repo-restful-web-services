package com.in28minutes.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

	// http://localhost:8080/v1/person
	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		
		PersonV1 person = new PersonV1("Bob Charlie");
		
		return person;
		
	}

	// http://localhost:8080/v2/person
	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		
		Name name = new Name("Bob", "Charlie");
		PersonV2 person = new PersonV2(name);
		
		return person;
		
	}

	// http://localhost:8080/person?version=1
	@GetMapping(path="/person", params="version=1")
	public PersonV1 getFirstVersionOfPersonParameter() {
		
		PersonV1 person = new PersonV1("Bob Charlie");
		
		return person;
		
	}
	
	// http://localhost:8080/person?version=2
	@GetMapping(path="/person", params="version=2")
	public PersonV2 getSecondVersionOfPersonParameter() {
		
		Name name = new Name("Bob", "Charlie");
		PersonV2 person = new PersonV2(name);
		
		return person;
		
	}

	// http://localhost:8080/person/header
	@GetMapping(path="/person/header", headers="X-API-VERSION=1")
	public PersonV1 getFirstVersionOfRequestHeader() {
		
		PersonV1 person = new PersonV1("Bob Charlie");
		
		return person;
		
	}
	
	// http://localhost:8080/person/header
	@GetMapping(path="/person/header", headers="X-API-VERSION=2")
	public PersonV2 getSecondVersionOfRequestHeader() {
		
		Name name = new Name("Bob", "Charlie");
		PersonV2 person = new PersonV2(name);
		
		return person;
		
	}

	// http://localhost:8080/person/accept
	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v1+json")
	public PersonV1 getFirstVersionOfAcceptHeader() {
		
		PersonV1 person = new PersonV1("Bob Charlie");
		
		return person;
		
	}

	// http://localhost:8080/person/accept
	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v2+json")
	public PersonV2 getSecondVersionOfAcceptHeader() {
		
		Name name = new Name("Bob", "Charlie");
		PersonV2 person = new PersonV2(name);
		
		return person;
		
	}
	
}
