package com.example.demo.services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.PageableResponse;
import com.example.demo.entities.Category;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.helpers.Helper;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		// random id create
		String categoryId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);
		Category category = mapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		return mapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {
		// get category by given id
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

		// update category details
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription()); // FIXED: used correct setter
		category.setCoverImage(categoryDto.getCoverImage());

		Category updatedCategory = categoryRepository.save(category); // FIXED: use lowercase `category`

		return mapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void delete(String categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
		categoryRepository.delete(category);
	}

	@Override
	public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort); // FIXED: added `sort`
		Page<Category> page = categoryRepository.findAll(pageable);
		return Helper.getPageableResponse(page, CategoryDto.class);
	}

	@Override
	public CategoryDto get(String categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
		return mapper.map(category, CategoryDto.class);
	}
}