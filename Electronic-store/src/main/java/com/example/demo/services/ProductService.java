package com.example.demo.services;

import com.example.demo.dto.PageableResponse;
import com.example.demo.dto.ProductDto;

public interface ProductService {

	// create
	ProductDto create(ProductDto productDto);

	// update
	ProductDto update(ProductDto productDto, String productId);

	// delete
	void delete(String productId);

	// get single product
	ProductDto get(String productId);
	// get all prodyct

//	List<ProductDto> getAll();
//	PageableResponse<ProductDto> getAll();
	// search product
//	List<ProductDto> getAllLive();
//	PageableResponse<ProductDto> getAllLive();

//	List<ProductDto> searchByTitle(String subTitle);
	PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy,
			String sortDir);

	PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

	PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

	//create product with category
	ProductDto createWithCategory(ProductDto productDto, String categoryId);
	
	//update category of product
	ProductDto updateCategory(String productId, String categoryId);
	
	//get all products of particular categpry
	PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir);
}
