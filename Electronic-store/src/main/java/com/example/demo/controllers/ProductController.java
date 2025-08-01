package com.example.demo.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ApiResponseMessage;
import com.example.demo.dto.ImageResponse;
import com.example.demo.dto.PageableResponse;
import com.example.demo.dto.ProductDto;
import com.example.demo.services.FileService;
import com.example.demo.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private FileService fileService;
	@Value("${product.image.path}")
	private String imagePath;

	// create product
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
		ProductDto createdProduct = productService.create(productDto);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
			@PathVariable String productId) {
		ProductDto updatedProduct = productService.update(productDto, productId);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) {
		productService.delete(productId); // This method handles internal deletion logic

		ApiResponseMessage response = new ApiResponseMessage();
		response.setMessage("pridyct deleted successfully");
		response.setSuccess(true);
		response.setStatus(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// get single product
	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
		ProductDto productDto = productService.get(productId);
		return new ResponseEntity<>(productDto, HttpStatus.OK);
	}

	// get all products
	@GetMapping
	public ResponseEntity<PageableResponse<ProductDto>> getAll(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
	}

	// get all live products
	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy,
				sortDir);
		return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
	}

	// search product
	@GetMapping("/search/{query}")
	public ResponseEntity<PageableResponse<ProductDto>> searchProduct(@PathVariable String query,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query, pageNumber, pageSize,
				sortBy, sortDir);
		return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
	}

	/// uploda product image
	@PostMapping("/image/{productId}")
	public ResponseEntity<ImageResponse> uploadProductImage(@PathVariable String productId,
			@RequestParam("productImage") MultipartFile image) throws IOException {
		// Upload the image file and get the filename
		String fileName = fileService.uploadFile(image, imagePath);

		// Fetch and update product with image name
		ProductDto productDto = productService.get(productId);
		productDto.setProductImageName(fileName);
		ProductDto updatedProduct = productService.update(productDto, productId);

		// Create ImageResponse without using builder
		ImageResponse response = new ImageResponse();
		response.setImageName(updatedProduct.getProductImageName());
		response.setMessage("Image uploaded successfully");
		response.setSuccess(true);
		response.setStatus(HttpStatus.CREATED);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// server the image
	@GetMapping("/image/{productId}")
	public void serveProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
		ProductDto productDto = productService.get(productId);
		// Get the image as an InputStream
		InputStream inputStream = fileService.getResource(imagePath, productDto.getProductImageName());

		// Set the content type (assumes image is jpeg/png — adjust as needed)
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);

		// Copy the image stream to the response output stream
		StreamUtils.copy(inputStream, response.getOutputStream());

		// Flush the response output
		response.flushBuffer();
	}

}
