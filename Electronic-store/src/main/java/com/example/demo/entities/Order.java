package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
	@Id
	private String orderId;

	// Pending dispatch deliveers will be the order status
	// enuum can be used here also
	private String orderStatus;

	// NOT-PAID, PAID
	// boolean false-> not paid true -> paid
	private String paymentStatus;

	private int orderAmount;
	@Column(length = 1000)
	private String billingAddress;

	private String billingPhone;
	private String billingName;
	private Date orderDate;
	private Date deliveredDate;

	// user order - one user can has many orders or one uses can order many times
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private Users user;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<OrderItem> orderItem = new ArrayList<>();

}
