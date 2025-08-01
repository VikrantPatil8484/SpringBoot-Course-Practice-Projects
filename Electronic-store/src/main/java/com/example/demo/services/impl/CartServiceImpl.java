package com.example.demo.services.impl;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddItemToCartRequest;
import com.example.demo.dto.CartDto;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Product;
import com.example.demo.entities.Users;
import com.example.demo.exceptions.BadApiRequest;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CartItemRepository;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.repositories.UsersRepo;
import com.example.demo.services.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UsersRepo userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
		int quantity = request.getQuantity();
		String productId = request.getProductId();
		
		if(quantity <= 0) {
			throw new BadApiRequest("request quantity is not valid");
		}

		// fetch the product
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found"));

		// fetch the user from db
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found in database"));

		Cart cart;
		try {
			cart = cartRepository.findByUser(user).get();
		} catch (NoSuchElementException e) {
			cart = new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setCreatedAt(new Date());
		}

		// perform cart operations
		
		List<CartItem> items = cart.getItems();
		AtomicReference<Boolean> updated = new AtomicReference<>(false);
		List<CartItem> updatedItems = items.stream().map(item -> {
			if (item.getProduct().getProductId().equals(productId)) {
				// item already presnt in cart
				item.setQuantity(quantity);
				item.setTotalPrice(quantity * product.getDiscountedPrice());
				updated.set(true);
			}
			return item;
		}).collect(Collectors.toList());

		

		if (!updated.get()) {
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setTotalPrice(quantity * product.getDiscountedPrice());
			cartItem.setCart(cart);
			cartItem.setProduct(product);

			updatedItems.add(cartItem);//add to the updates list of items
		}

		// create item without builder
		cart.setItems(updatedItems);
		cart.setUser(user);

		Cart updatedCart = cartRepository.save(cart);
		return modelMapper.map(updatedCart, CartDto.class);

	}
	
	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public void removeItemFromCart(String userId, int cartItem) {
		//conditions to throw exception
		//to remove we need cartitem repo
		CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(()-> new ResourceNotFoundException("cart item not found"));
		cartItemRepository.delete(cartItem1);

	}

	@Override
	public void clearCart(String userId) {
		//fetch the user first then get cart and then write logic for clear cart
		Users user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found in db"));
		Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("cart of given user not found in db"));
		cart.getItems().clear();
		cartRepository.save(cart);

	}

	@Override
	public CartDto getCartByUser(String userId) {
		Users user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found in db"));
		Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("cart of given user not found in db"));
		return modelMapper.map(cart, CartDto.class);
	}

}
