package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class home {
	
	@RequestMapping("/home")
    public String sayHello() {
        return "Hello from Spring Boot!";
    }

}
