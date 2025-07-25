package com.example.demo.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		User savedUser =userRepository.save(user);
		logger.info("user saved : {}", savedUser.getId());
		return savedUser;
	}

	@Override
	public User updateUser(User user, int userId) {
//		1.get use from db
		User user1 = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("user not foud"));
		user1.setName(user.getName());
		user1.setCity(user.getCity());
		user1.setAge(user.getAge());
		
//		2.update user
		User savUser = userRepository.save(user1);
		return savUser;
		
	}

	@Override
	public void deleteUser(int userId) {
		
		User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("user not found"));
		userRepository.delete(user);
		System.out.println("user deleted successfully");
		
	}

	@Override
	public List<User> getAllUser() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public User getUser(int userId) {
		Optional<User> userOptional = userRepository.findById(userId);
		User user = userOptional.orElseThrow(()-> new RuntimeException("user with id not found"));
		return user;
	}

}
