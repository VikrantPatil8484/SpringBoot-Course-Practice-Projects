package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping("/login")
	public String showLoginHandler() {
		return "login";
	}
	
	@GetMapping("/feedbacks")
	public List<String> getFeedbacks(){
		List<String> feedbacks = Arrays.asList(
				"good course",
				"nice one",
				"valuable thing");
		return feedbacks;
	}

}
