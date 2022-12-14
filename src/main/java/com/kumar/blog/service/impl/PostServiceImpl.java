package com.kumar.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.kumar.blog.payloads.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kumar.blog.entities.Category;
import com.kumar.blog.entities.Post;
import com.kumar.blog.entities.User;
import com.kumar.blog.exceptions.ResourceNotFoundException;
import com.kumar.blog.payloads.PostDto;
import com.kumar.blog.repositories.CategoryRepo;
import com.kumar.blog.repositories.PostRepo;
import com.kumar.blog.repositories.UserRepo;
import com.kumar.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User", "UserId", userId)
				);
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException("Category ", "categoryId", categoryId));

		// PostDto inside to field but to convert Post also many fields
		Post post  = this.modelMapper.map(postDto, Post.class); // convert postDto to post
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);

		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("post", "PostId", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatePost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("post", "PostId", postId));
		
		this.postRepo.delete(post);
		
	}

	// get all post
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		// pagegination
//		int pageSize = 5;
//		int pageNumber = 1;

//		Sort sort = null;
//		if(sortDir.equalsIgnoreCase("asc")){
//			sort = Sort.by(sortBy).ascending();
//		}else {
//			sort = Sort.by(sortBy).descending();
//		}
//		Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).descending());

		Sort sort = (sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNumber,pageSize, sort);

		Page<Post> pagePost = this.postRepo.findAll(p);

		List<Post> allPosts = pagePost.getContent();
//		List<Post> allPosts = this.postRepo.findAll();
		
		List<PostDto> postDtos = allPosts.stream().map( (post) -> this.modelMapper.map(post, PostDto.class)
				).collect(Collectors.toList());
		
//		return postDtos;
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());

		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return  postResponse;
	}

	// single post
	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("post", "PostId", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category" ,"category id", categoryId));
		
		List<Post> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> postDtos = posts.stream().map( (post) -> this.modelMapper.map(post, PostDto.class)
				).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {

		 User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user" ,"user id", userId));
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDtos = posts.stream().map( (post) -> this.modelMapper.map(post, PostDto.class)
				).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);

		return posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

}
