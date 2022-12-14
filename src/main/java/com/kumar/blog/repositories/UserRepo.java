package com.kumar.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kumar.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
