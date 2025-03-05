package com.in28minutes.restfulwebservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.restfulwebservices.entity.Post;
import com.in28minutes.restfulwebservices.entity.User;
import com.in28minutes.restfulwebservices.exception.UserNotFoundException;
import com.in28minutes.restfulwebservices.repo.PostRepository;
import com.in28minutes.restfulwebservices.repo.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

	//
	private UserRepository userRepo;
	private PostRepository postRepo;

	public UserJpaController(UserRepository userRepo, PostRepository postRepo) {
		super();
		this.userRepo = userRepo;
		this.postRepo = postRepo;
	}

	// GET /users 
	@GetMapping("/users")
	//@Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista con todos los usuarios")
	public List<User> retrieveAllUsers() {
		
		return userRepo.findAll();
		
	}
	
	// GET /user 
	@GetMapping("/users/{id}")
	//@Operation(summary = "Obtener datos de un usuario", description = "Retorna los datos del usuario a partir del ID proporcionado")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		
		Optional<User> userOpt = userRepo.findById(id);
		
		if (userOpt.isEmpty())
			throw new UserNotFoundException("id: " + String.valueOf(id));
		
		User user = userOpt.get();
		
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
		
		User savedUser = userRepo.save(user);
		
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
		
		userRepo.deleteById(id);
		
	}
	
	// Obtener listado de Post asociados a un Usuario
	@GetMapping("/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id) {
		
		Optional<User> userOpt = userRepo.findById(id);
		
		if (userOpt.isEmpty())
			throw new UserNotFoundException("id: " + id);
		
		User user = userOpt.get();
		
		List<Post> listPost = user.getPosts();
		
		return listPost;
		
	}
	
	// Creacion de Post para un Usuario determinado
	// El Usuario se envia en la URL y el Post se pasa en el RequestBody
	@PostMapping("/users/{id}/posts")
	//@Operation(summary = "Creacion de Post de usuario", description = "Crea un Post para un Usuario concreto a partir de los datos proporcionados")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		
		List<Post> listaPost = new ArrayList<Post>();
		
		// Obtenemos el usuario a partir del id 
		Optional<User> userOpt = userRepo.findById(id);
		if (userOpt.isEmpty())
			throw new UserNotFoundException("id: " + id);
		User user = userOpt.get();
		
		// Añadimos el Usuario al objeto Post
		post.setUser(user);
		
		Post savedPost = postRepo.save(post);
		
		// Recuperamos la lista actualizada de Post del usuario
		listaPost = user.getPosts();
		
		// Creamos una URL que apunte al endpoint de obtencion del Post que acabamos de crear
		// CurrentRequest ---> /jpa/users/{id}/posts
		// Path /{id}     ---> /jpa/users/{id}/posts/{id}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();

		// En la entidad de respuesta almacenamos la URL que hemos creado
		ResponseEntity re = ResponseEntity.created(location).build();

		return re;
		
	}
	
}
