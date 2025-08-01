package com.example.demo.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ApiResponseMessage;
import com.example.demo.dto.ImageResponse;
import com.example.demo.dto.PageableResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.services.FileService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Value("${user.profile.image.path}")
	private String imageUploadPath;

//	create user
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto userDto1 = userService.createUser(userDto);
		return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
	}

//	update user - userId is path uri variabls
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") String userId,
			@RequestBody UserDto userDto) {
		UserDto updatedUserDto = userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
	}

//	delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);

		ApiResponseMessage message = new ApiResponseMessage();
		message.setMessage("user deleted");
		message.setSuccess(true);
		message.setStatus(HttpStatus.OK);

		return new ResponseEntity<>(message, HttpStatus.OK);
	}

//	get all users
	@GetMapping
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

		PageableResponse<UserDto> response = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	get single user
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
		return new ResponseEntity<>(userService.getUserByIdEmail(email), HttpStatus.OK);
	}

//	search user
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
		return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
	}

//	get user by id 
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
		UserDto userDto = userService.getUserById(userId);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

//	upload user image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image,
			@PathVariable String userId) throws IOException {

		String imageName = fileService.uploadFile(image, imageUploadPath);

		// Update image name in user profile
		UserDto user = userService.getUserById(userId);
		user.setImageName(imageName);
		UserDto updatedUser = userService.updateUser(user, userId);

		// Create response without builder
		ImageResponse imageResponse = new ImageResponse();
		imageResponse.setImageName(imageName);
		imageResponse.setSuccess(true);
		imageResponse.setStatus(HttpStatus.CREATED);

		return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
	}

//	server user image
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
		UserDto user = userService.getUserById(userId);
		 InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
		 
		 response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		 StreamUtils.copy(resource, response.getOutputStream());
		 
	}
}
