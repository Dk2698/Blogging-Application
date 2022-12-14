package com.kumar.blog.services;

import com.kumar.blog.entities.User;
import com.kumar.blog.payloads.UserDto;

import java.util.List;

public interface UserService {
    // not direct passing entity
//    User createUser(User user);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);
}
