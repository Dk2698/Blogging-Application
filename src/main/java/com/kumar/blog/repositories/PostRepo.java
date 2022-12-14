package com.kumar.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kumar.blog.entities.Category;
import com.kumar.blog.entities.Post;
import com.kumar.blog.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//create owe method for query
public interface PostRepo extends JpaRepository<Post, Integer>{
    // jpa create runtime impl
    // all post
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    //like query depends on hibernate version
//    @Query("select p from Post p where p.title like:key")
//    List<Post> findByTitleContaining(@Param("key") String title);
    List<Post> findByTitleContaining(String title);

}
