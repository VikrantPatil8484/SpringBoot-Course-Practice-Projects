package com.example.demo.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	@Id
	private String cartId;
	private Date createdAt;
	@OneToOne
	private Users user;
	// mapping cart items
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	@JsonManagedReference // optional; only if you’re serializing the cart in JSON
	private List<CartItem> items = new ArrayList<>();

}
