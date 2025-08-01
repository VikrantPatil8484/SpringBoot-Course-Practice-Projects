package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.entities.CartItem;
import com.example.demo.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

	private String cartId;
	private Date createdAt;

	private Users user;

	private List<CartItem> items = new ArrayList<>();

}
