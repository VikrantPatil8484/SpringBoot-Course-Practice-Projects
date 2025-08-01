package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDto {

	private String orderId;

	private String orderStatus="PENDING";

	private String paymentStatus="NOTPAID";

	private int orderAmount;

	private String billingAddress;

	private UserDto user;

	private String billingPhone;
	private String billingName;
	private Date orderDate=new Date();
	private Date deliveredDate;

	
	

	private List<OrderItemDto> orderItems = new ArrayList<>();

}
