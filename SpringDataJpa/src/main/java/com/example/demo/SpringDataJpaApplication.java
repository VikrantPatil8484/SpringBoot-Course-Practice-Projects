package com.example.demo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;

@SpringBootApplication
public class SpringDataJpaApplication implements CommandLineRunner{
	
	@Autowired
	private UserService userService;
	
	Logger logger = LoggerFactory.getLogger(SpringDataJpaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
//		User user = new User();
//		user.setName("vicky");
//		user.setCity("mumbai");
//		user.setAge(24);
//		User savedUser = userService.saveUser(user);
		
//		get single user
//		List<User> alluser = userService.getAllUser();
//		logger.info("all user : {}", alluser);
//		logger.info("size :{}", alluser.size());
		
//		User user = userService.getUser(2);
//		logger.info("user is : {}", user);
		
//		User user = new User();
//		user.setName("vikrant patil");
//		user.setCity("pune");
//		user.setAge(24);
//		User updatedUser = userService.updateUser(user, 1);
//		logger.info("updated user : {}", updatedUser);
		
		userService.deleteUser(2);
	}
}
