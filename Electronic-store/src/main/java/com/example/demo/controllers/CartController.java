package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AddItemToCartRequest;
import com.example.demo.dto.ApiResponseMessage;
import com.example.demo.dto.CartDto;
import com.example.demo.services.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId,
			@RequestBody AddItemToCartRequest request) {
		CartDto cartDto = cartService.addItemToCart(userId, request);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}/items/{itemId}")
	public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId,
			@PathVariable Integer itemId) {
		cartService.removeItemFromCart(userId, itemId);

		ApiResponseMessage response = new ApiResponseMessage();
		response.setMessage("item is removed");
		response.setSuccess(true);
		response.setStatus(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId) {
		cartService.clearCart(userId);

		ApiResponseMessage response = new ApiResponseMessage();
		response.setMessage("cart is blanked, To see add item to cart");
		response.setSuccess(true);
		response.setStatus(HttpStatus.OK);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
		CartDto cartDto = cartService.getCartByUser(userId);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}

}
