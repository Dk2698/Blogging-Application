package com.kumar.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kumar.blog.entities.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    // create method
    Optional<User> findByEmail(String email);

}
