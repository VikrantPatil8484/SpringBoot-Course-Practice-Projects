package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private String categoryId;

	@NotBlank
	@Size(min = 4, message = "title must be of min 4 characters")
	private String title;

	@NotBlank(message = "desc required")
	private String description;

	@NotBlank(message = "cover image is required")
	private String coverImage;

}
