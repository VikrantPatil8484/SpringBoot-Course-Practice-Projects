package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.PageableResponse;
import com.example.demo.dto.UserDto;

public interface UserService {

// create user
	UserDto createUser(UserDto userDto);

//	update
	UserDto updateUser(UserDto userDto, String userId);

//	delete
	void deleteUser(String userId);

//	get all users
	PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);


//	get single user by id
	UserDto getUserById(String userId);

//	get single user email id
	UserDto getUserByIdEmail(String email);

//	search user
	List<UserDto> searchUser(String keyword);

}
