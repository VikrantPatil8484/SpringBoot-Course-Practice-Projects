package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/project")
public class SuperMan {

	/*
	 * /about
	 * 
	 * @RequestMapping is used for mapping the url with method
	 */
	@RequestMapping("/about")
	public String aboutRequestHandler() {
		System.out.println("processing about request");
		return "about";
	}
	
	@RequestMapping("/services")
	public String serviceRequestHandler() {
		System.out.println("processing about request");
		return "services";
	}
}
