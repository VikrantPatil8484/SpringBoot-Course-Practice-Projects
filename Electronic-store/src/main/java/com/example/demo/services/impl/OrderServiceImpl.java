package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.PageableResponse;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Order;
import com.example.demo.entities.OrderItem;
import com.example.demo.entities.Users;
import com.example.demo.exceptions.BadApiRequest;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.helpers.Helper;
import com.example.demo.repositories.CartRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UsersRepo;
import com.example.demo.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UsersRepo userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CartRepository cartRepository;

//	@Override
//	public OrderDto createOrder(CreateOrderRequest orderDto, String userId, String cartId) {
//		// fetch user by userid
//
//		Users user = userRepository.findById(userId)
//				.orElseThrow(() -> new ResourceNotFoundException("user not found with given ID"));
//		// corresponding to user generating order
//		// fetch cart
//		Cart cart = cartRepository.findById(cartId)
//				.orElseThrow(() -> new ResourceNotFoundException("cart with given ID not found"));
//		List<CartItem> cartItems = cart.getItems();
//		if (cartItems.size() <= 0) {
//			throw new BadApiRequest("Invalid number of items in cart!!");
//		}
//
//		// if evrything is find then generating order
//		Order order = Order.builder().billingName(orderDto.getBillingName()).billingPhone(orderDto.getBillingPhone())
//				.billingAddress(orderDto.getBillingAddress()).orderDate(new Date()).deliveredDate(null)
//				.paymentStatus(orderDto.getPaymentStatus()).orderStatus(orderDto.getOrderStatus())
//				.orderId(UUID.randomUUID().toString()).user(user).build();
//
//		// setting orderItem and amount
//		AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
//
//		List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
//			int totalPrice = cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice();
//			orderAmount.set(orderAmount.get() + totalPrice); // ✅ FIXED
//			return OrderItem.builder().quantity(cartItem.getQuantity()).product(cartItem.getProduct())
//					.totalPrice(totalPrice).order(order).build();
//		}).collect(Collectors.toList());
//
//		order.setOrderItem(orderItems);
//		order.setOrderAmount(orderAmount.get()); // ✅ Will now be correct
//
//		List<CartItem> cartItems1 = cart.getItems();
//
//		// Break bidirectional link
//		for (CartItem item : cartItems1) {
//			item.setCart(null);
//		}
//
//		// Clear and reset reference
//		cart.setItems(new ArrayList<>());
//
//		cartRepository.save(cart);
//
////		cartRepository.save(cart);
//		Order savedOrder = orderRepository.save(order);
//
//		return modelMapper.map(savedOrder, OrderDto.class);
//	}

	@Override
	public OrderDto createOrder(CreateOrderRequest orderDto, String userId, String cartId) {
		// 1. Fetch user by userId
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with given ID"));

		// 2. Fetch cart by cartId
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found with given ID"));

		List<CartItem> cartItems = cart.getItems();

		if (cartItems == null || cartItems.isEmpty()) {
			throw new BadApiRequest("Invalid number of items in cart!");
		}

		// 3. Create Order
		Order order = Order.builder().billingName(orderDto.getBillingName()).billingPhone(orderDto.getBillingPhone())
				.billingAddress(orderDto.getBillingAddress()).orderDate(new Date()).deliveredDate(null)
				.paymentStatus(orderDto.getPaymentStatus()).orderStatus(orderDto.getOrderStatus())
				.orderId(UUID.randomUUID().toString()).user(user).build();

		// 4. Convert CartItems to OrderItems
		AtomicReference<Integer> orderAmount = new AtomicReference<>(0);

		List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
			int totalPrice = cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice();
			orderAmount.set(orderAmount.get() + totalPrice);
			return OrderItem.builder().quantity(cartItem.getQuantity()).product(cartItem.getProduct())
					.totalPrice(totalPrice).order(order).build();
		}).collect(Collectors.toList());

		order.setOrderItem(orderItems);
		order.setOrderAmount(orderAmount.get());

		// 5. Handle cart orphan removal (IMPORTANT)
		List<CartItem> cartItemsCopy = new ArrayList<>(cart.getItems());

		for (CartItem item : cartItemsCopy) {
			item.setCart(null); // break the reference
		}

		cart.setItems(new ArrayList<>()); // ensure Hibernate detects new collection

		cartRepository.save(cart); // save cart first

		// 6. Save order
		Order savedOrder = orderRepository.save(order);

		return modelMapper.map(savedOrder, OrderDto.class);
	}

	@Override
	public void removeOrder(String orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("order not found to deleete"));
		orderRepository.delete(order);

	}

	@Override
	public List<OrderDto> getOrdersOfUser(String userId) {
		Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
		List<Order> orders = orderRepository.findByUser(user);
		List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class))
				.collect(Collectors.toList());
		return orderDtos;
	}

	@Override
	public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Order> page = orderRepository.findAll(pageable);
		return Helper.getPageableResponse(page, OrderDto.class);
	}

}
