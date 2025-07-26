package com.example.demo.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.first.config.LCWDConfig;

@RestController
public class HomeController {
	
	@Value("${lcwd.profile.image.path}")
	private String profilePath;
	
	
	@Autowired
	private LCWDConfig lcwdConfig;

	
	@RequestMapping("/todos")
	public List<String> justTest(){
		List<String> todos = Arrays.asList("Learn Java", "Learn spring boot", "do projects");
		return todos;
	}

	@RequestMapping("/profile-path")
	public String getProfilePath() {
		return this.profilePath;
	}
	
	public LCWDConfig getLcwdConfig() {
		
		return this.lcwdConfig;
	}
	
}
