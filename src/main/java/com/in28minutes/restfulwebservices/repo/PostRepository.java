package com.in28minutes.restfulwebservices.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.restfulwebservices.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
