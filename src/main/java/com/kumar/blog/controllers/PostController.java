package com.kumar.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.kumar.blog.configuration.AppConstants;
import com.kumar.blog.entities.Post;
import com.kumar.blog.payloads.PostResponse;
import com.kumar.blog.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import com.kumar.blog.payloads.ApiResponse;
import com.kumar.blog.payloads.PostDto;
import com.kumar.blog.payloads.UserDto;
import com.kumar.blog.services.PostService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}") // this value get from properties
	private  String path;
	// create post
//	public ResponseEntity<PostDto> createPost(
//			@RequestBody PostDto postDto,
//			@PathVariable Integer userId,
//			@PathVariable Integer categoryId
//			){
//		
//		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
//		
//		return new ResponseEntity<>(createPost, HttpStatus.CREATED);
		
	//}
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createUser(
			@RequestBody PostDto postDto, 
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<>(createPost, HttpStatus.CREATED);
	}
	
	// get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
		// get by category
		@GetMapping("/category/{categoryId}/posts")
		public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
			
			List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
			
			return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		}
		
		// get all posts
//		@GetMapping("/posts")
//		public ResponseEntity<List<PostDto>> getAllPost(
//				@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//				@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize
//		){
//
//			List<PostDto> allPosts = this.postService.getAllPost(pageNumber, pageSize);
//
//			return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
//		}
	//implement by  pagination
//	@GetMapping("/posts")
//	public ResponseEntity<PostResponse> getAllPost(
//			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize
//	){
//
//		 PostResponse postResponse= this.postService.getAllPost(pageNumber, pageSize);
//
//		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
//	}

	//implement by sorting
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
	){

		PostResponse postResponse= this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);

		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
		
		// get post details by id
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
			
			PostDto postDto = this.postService.getPostById(postId);
					
			return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
		}
	
//		delete post
		@DeleteMapping("/posts/{postId}")
		public ApiResponse deletePost(@PathVariable Integer postId) {
			this.postService.deletePost(postId);
			return new ApiResponse("post is successfully delete", true);
		}
		// update post
		@PutMapping("/posts/{postId}")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
			PostDto updatePost = this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
		}

		// pageSize and pageNumber
	// sorting by any one field
	// http://localhost://9090/posts?pageSize=5&pageNo=2&sortedBy=title
	//search
		@GetMapping("/posts/search/{keywords}")
		public ResponseEntity<List<PostDto>> getPostById(@PathVariable("keywords") String keywords){

			List<PostDto> result = this.postService.searchPosts(keywords);

			return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
		}
		// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image")MultipartFile image,
			@PathVariable Integer postId
			) throws IOException {
		PostDto postDto = this.postService.getPostById(postId);

		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto,postId);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	// localhost:9090/images/abc.png
	// method to serve files
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
	) throws IOException {

		InputStream resources = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);

		StreamUtils.copy(resources, response.getOutputStream());
	}

}
