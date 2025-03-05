package com.in28minutes.restfulwebservices.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.restfulwebservices.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
