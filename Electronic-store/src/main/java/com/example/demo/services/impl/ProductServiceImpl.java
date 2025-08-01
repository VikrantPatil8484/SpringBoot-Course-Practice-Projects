package com.example.demo.services.impl;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.PageableResponse;
import com.example.demo.dto.ProductDto;
import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.helpers.Helper;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired	
	private CategoryRepo categoryRepository;

	@Override
	public ProductDto create(ProductDto productDto) {

		Product product = mapper.map(productDto, Product.class);
		// random product id generate
		String productId = UUID.randomUUID().toString();
		product.setProductId(productId);
		product.setAddedDate(new Date());
		Product saveProduct = productRepository.save(product);
		return mapper.map(saveProduct, ProductDto.class);
	}

	@Override
	public ProductDto update(ProductDto productDto, String productId) {
		// fetch the product of given id
		Product product = productRepository.findById(productId).orElseThrow();
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setQuantity(productDto.getQuantity());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());
		product.setProductImageName(productDto.getProductImageName());
		Product updatedProduct = productRepository.save(product);
		return mapper.map(updatedProduct, ProductDto.class);

	}

	@Override
	public void delete(String productId) {
		Product product = productRepository.findById(productId).orElseThrow();
		productRepository.delete(product);

	}

	@Override
	public ProductDto get(String productId) {
		Product product = productRepository.findById(productId).orElseThrow();
		return mapper.map(product, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findAll(pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByLiveTrue(pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable); // ✅
		return Helper.getPageableResponse(page, ProductDto.class); // ✅ correct usage
	}

	@Override
	public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
	    // 1. Fetch category from DB
	    Category category = categoryRepository.findById(categoryId)
	            .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

	    // 2. Map ProductDto to Product entity
	    Product product = mapper.map(productDto, Product.class);

	    // 3. Set system-generated fields
	    product.setProductId(UUID.randomUUID().toString());
	    product.setAddedDate(new Date());
	    product.setCategory(category); // assign category

	    // 4. Save product
	    Product savedProduct = productRepository.save(product);

	    // 5. Map saved product to DTO
	    ProductDto responseDto = mapper.map(savedProduct, ProductDto.class);

	    // 6. Explicitly map and set category
	    responseDto.setCategory(mapper.map(category, CategoryDto.class));

	    return responseDto;
	}

	@Override
	public ProductDto updateCategory(String productId, String categoryId) {
		//product fetch operation
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("product with given category not found"));
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("product with given category not found"));
		product.setCategory(category);
		Product savedProduct = productRepository.save(product);
		return mapper.map(savedProduct, ProductDto.class);
		
	}

	@Override
	public PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("product with given category not found"));
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByCategory(category, pageable);
		return Helper.getPageableResponse(page,  ProductDto.class);
	}


}
