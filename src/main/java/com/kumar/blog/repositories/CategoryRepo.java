package com.kumar.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kumar.blog.entities.Category;

// interface

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
