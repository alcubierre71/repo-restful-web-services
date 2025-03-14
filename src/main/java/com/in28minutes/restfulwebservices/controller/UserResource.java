package com.in28minutes.restfulwebservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.restfulwebservices.entity.User;
import com.in28minutes.restfulwebservices.exception.UserNotFoundException;
import com.in28minutes.restfulwebservices.repo.UserDaoService;

import jakarta.validation.Valid;

@RestController
public class UserResource {

	//
	private UserDaoService service;

	public UserResource(UserDaoService service) {
		super();
		this.service = service;
	}

	// GET /users 
	@GetMapping("/users")
	//@Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista con todos los usuarios")
	public List<User> retrieveAllUsers() {
		
		return service.findAll();
		
	}
	
	// GET /user 
	@GetMapping("/users/{id}")
	//@Operation(summary = "Obtener datos de un usuario", description = "Retorna los datos del usuario a partir del ID proporcionado")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		
		User user = service.findOne(id);
		
		if (user == null)
			throw new UserNotFoundException("id: " + String.valueOf(id));
		
		// Creacion de EntityModel con el objeto User
		EntityModel<User> entityModel = EntityModel.of(user);
		
		// Creacion de Link Builder
		WebMvcLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		// Creacion de Link con la relacion con el objeto
		String rel = "all-users";
		Link link = linkBuilder.withRel(rel);
		
		// Añadir links al EntityModel del objeto
		entityModel.add(link);
		
		return entityModel;
		
	}
	
	// POST /user
	// {"id":3,"name":"Catwoman","birthDate":"1990-02-27"}
	@PostMapping("/users")
	//@Operation(summary = "Creacion de usuario", description = "Crea un usuario a partir de los datos proporcionados")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(savedUser.getId())
						.toUri();
		
		ResponseEntity re = ResponseEntity.created(location).build();
		
		return re;
		
	}
	
	// DELETE /user 
	@DeleteMapping("/users/{id}")
	//@Operation(summary = "Eliminar usuario", description = "Elimina un usuario a partir del ID proporcionado")
	public void deleteUser(@PathVariable int id) {
		
		boolean deleteUser = service.deleteById(id);
		
	}
	
	
	
}
