package com.example.demo.services;

import java.util.List;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.PageableResponse;

public interface OrderService {
	
	//to create order
	OrderDto createOrder(CreateOrderRequest orderDto, String userId, String cartId);
	//to remove order
	void removeOrder(String orderId);
	
	//get orders of users
	List<OrderDto> getOrdersOfUser(String userId);
	
	//get orders all
	PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//other methods relared to order

}
