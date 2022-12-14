package com.kumar.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kumar.blog.entities.*;
import com.kumar.blog.payloads.UserDto;
import com.kumar.blog.repositories.*;
import com.kumar.blog.services.UserService;
import com.kumar.blog.exceptions.*;


@Service
public class UserServiceImpl  implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// userDto having to convert User entity
		//  convert userDTO to User
		User user = this.dtoToUser(userDto);
		// save  User entity into db
		User savedUser = this.userRepo.save(user);
		// covert User to UserDTO and return them
		return this.userToDto(savedUser);
	}
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
//		get from User Table
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User"," id ", userId));
		// get name from userDTo means use from client
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		// save in database user Table
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() ->  new ResourceNotFoundException("User", "id", userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User>  users= this.userRepo.findAll();
		// 
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
						.orElseThrow(() ->  new ResourceNotFoundException("User", "id", userId));
		
		this.userRepo.delete(user);
		
		
	}
	// this method used modelMapper library
	// also created multiple method and field is different map to manually to setter method
	// convert userDTO to user
	private User dtoToUser(UserDto userDto) {
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		// here used modelMapper bean(sourec , classname
		User user = this.modelMapper.map(userDto,User.class);
		
		return user;
	}

	// convert user to userDto
	private UserDto userToDto(User user) {
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		UserDto userDto = this.modelMapper.map(user,UserDto.class);

		return userDto;
	}

	
}
