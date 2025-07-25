package com.example.demo.services;

import java.util.List;

import com.example.demo.entities.User;

public interface UserService {

	User saveUser(User user);

	User updateUser(User user, int userId);

	void deleteUser(int userId);

	List<User> getAllUser();

	User getUser(int userId);

}
