package com.example.demo.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.PageableResponse;

public interface CategoryService {

	//create category
	CategoryDto create(CategoryDto categoryDto);
	
	//update
	CategoryDto update(CategoryDto categoryDto, String categoryId);
	
	//delete
	void delete(String categoryId);
	
	//get all categories
	PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//get single catgeory detail
	CategoryDto get(String categoryId);
}

//loose coupling - written interfaces then their implementation has written
//writing interfaces and their implementations in separate packages is a key practice for achieving loose coupling in your application.
//Loose coupling means components of your system are minimally dependent on each other.