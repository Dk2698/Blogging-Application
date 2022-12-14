package com.kumar.blog.services;

import java.util.List;

import com.kumar.blog.payloads.PostDto;
import com.kumar.blog.payloads.PostResponse;

public interface PostService {
	// create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	// delete
	
	void deletePost(Integer postId);
	// get all post 
//	List<PostDto> getAllPost(Integer pageNumber,Integer pageSize);
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get single post
	PostDto getPostById(Integer postId);
	
	// get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get all posts by user
	List<PostDto> getPostsByUser(Integer userId);

	//search post
	 List<PostDto> searchPosts(String keyword) ;

}