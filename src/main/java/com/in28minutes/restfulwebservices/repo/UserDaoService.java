package com.in28minutes.restfulwebservices.repo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.in28minutes.restfulwebservices.entity.User;

@Component 
public class UserDaoService {

	private static List<User> users = new ArrayList<User>();
	
	private static int usersCount = 0;
	
	static {
		users.add(new User( ++usersCount, "Adam", LocalDate.now().minusYears(30) ));
		users.add(new User( ++usersCount, "Petete", LocalDate.now().minusYears(40) ));
		users.add(new User( ++usersCount, "Catwoman", LocalDate.now().minusYears(35) ));
	}
	
	// Obtener todos los usuarios
	public List<User> findAll() {
	
		return users;
		
	}
	
	// Obtener un usuario
	public User findOne(int id) {
		
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		
		User user = users.stream().filter(predicate).findFirst().orElse(null);
		
		return user;
		
	}
	
	// Crear nuevo usuario
	public User save(User user) {
		
		user.setId(++usersCount);
		
		users.add(user);
		
		return user;
	}
	
	// Eliminar un usuario
	public boolean deleteById(int id) {
		
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		
		//User user = users.stream().filter(predicate).findFirst().orElse(null);
		
		boolean deleteUser = users.removeIf(predicate);
		
		return deleteUser;
		
	}
	
	
}
