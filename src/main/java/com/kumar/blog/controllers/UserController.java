package com.kumar.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kumar.blog.payloads.ApiResponse;
import com.kumar.blog.payloads.UserDto;
import com.kumar.blog.services.UserService;

// this class use service Implement

@RestController
@RequestMapping("/api/users")
public class UserController {
	// interface create user service class
	// implement class create to instance using autoWire
	
	@Autowired
	private UserService userService;
	// inject by constructor
	
	// post create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	// put update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid ){
		UserDto updatedUserDto = this.userService.updateUser(userDto, uid);
		return  ResponseEntity.ok(updatedUserDto);
	}
	// Delete - delete user to access only admin
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
//		this.deleteUser(userId);
		this.userService.deleteUser(userId);
//		return new ResponseEntity(Map.of("message", "User Deleted successfully"), HttpStatus.OK);
		return  new  ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}
	
	// get user get
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getAllUser(@PathVariable Integer userId){
		
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
}
