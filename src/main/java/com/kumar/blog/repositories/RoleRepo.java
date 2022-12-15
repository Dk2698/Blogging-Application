package com.kumar.blog.repositories;

import com.kumar.blog.entities.Category;
import com.kumar.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepo extends JpaRepository<Role, Integer> {

}
