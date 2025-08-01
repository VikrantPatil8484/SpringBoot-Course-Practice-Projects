package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

	@NotBlank(message = "cart id is required!")
	private String cartId;
	@NotBlank(message = "user id is required!")
	private String userId;

	@NotBlank(message = "status is required!")
	private String orderStatus = "PENDING";

	private String paymentStatus = "NOTPAID";

//	private int orderAmount;
	@NotBlank(message = "address is required!")
	private String billingAddress;

//	private UserDto user;
	@NotBlank(message = "Phone number is required!")
	private String billingPhone;
	@NotBlank(message = "Billing name is required!")
	private String billingName;

}
