package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	
	Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@GetMapping("/getProduct")
	public String getProduct(@RequestParam("productName") String name,
			@RequestParam("productRating") int rating,
			@RequestParam("productId") int id) {
		System.out.println("productName : " + name);
		System.out.println("productId : " + id);
		System.out.println("productrating :" + rating);
		return "This is for testing product url";
	}

	
	@RequestMapping("/checkProduct/{productId}/{productName}/{productRating}")
	public String checkProduct(
			@PathVariable("productId") int productId,
			@PathVariable String productName,
			@PathVariable int productRating) {
//		System.out.println("productName : " + productName);
//		System.out.println("productId :" + productId);
//		System.out.println("productRating : " + productRating);
		logger.error("productName" + productName);
		logger.warn("productId"+productId);
		logger.info("productRating"+productRating);
		return "checking the concept of path variable";
	}
}
