package com.example.demo.services;

import com.example.demo.dto.AddItemToCartRequest;
import com.example.demo.dto.CartDto;

public interface CartService {
	

	// add items to cart when ever user adding data into cart then at that time cart
	// for that user is not available then we will create cart for that user ad then
	// add the item
	// if cart available then directly add data items to cart
	CartDto addItemToCart(String userId, AddItemToCartRequest request);

	// remove item from cart
	void removeItemFromCart(String userId, int cartItem);

	//remove all items from cart
	void clearCart(String userId);
	
	CartDto getCartByUser(String userId);
}
