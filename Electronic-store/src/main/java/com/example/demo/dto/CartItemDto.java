package com.example.demo.dto;

import com.example.demo.entities.Cart;
import com.example.demo.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

	private int cartItemId;

	private Product product;

	private int quantity;
	private int totalPrice;

	private Cart cart;

}
