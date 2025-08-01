package com.example.demo.dto;

import com.example.demo.validate.ImageNameValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {

	private String userId;

	@Size(min = 3, max = 15, message = "Invalid name!")
	private String name;

	

//	@Email(message = "Invalid user email!")
	@Pattern(regexp = "^[a-z0-9][-a-z0-9.-]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid email format")
	@NotBlank(message = "email required")
	private String email;

	@NotBlank(message = "password is required!")
	private String password;

	@Size(min = 4, max = 6, message = "Invalid gender")
	private String gender;

	@NotBlank(message = "write something about you")
	private String about;

//	@pattern and custom validator

	@ImageNameValid
	private String imageName;

}
