package com.example.demo.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PageableResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.Users;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.helpers.Helper;
import com.example.demo.repositories.UsersRepo;
import com.example.demo.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepo usersRepo;
	
	//setting password encode while creating user
	@Autowired
	private PasswordEncoder passwordEncoder;

	// importing modelmapper class here
	@Autowired
	private ModelMapper mapper;

	@Value("${user.profile.image.path}")
	private String imagePath;

	@Override
	public UserDto createUser(UserDto userDto) {
//		generate unique id in string format
		String userId = UUID.randomUUID().toString();
		userDto.setUserId(userId);
		//encoding password
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		// Convert DTO -> entity
		Users user = dtoToEntity(userDto);

		// Save entity to DB
		Users savedUser = usersRepo.save(user);

		// Convert entity -> dto
		UserDto newDto = entityToDto(savedUser);
		return newDto;

	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		// Fetch user or throw exception
		Users user = usersRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		// Update user fields
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setGender(userDto.getGender());
		user.setImageName(userDto.getImageName());

		// Save the updated user entity
		Users updatedUser = usersRepo.save(user);

		// Convert updated entity back to DTO and return
		return entityToDto(updatedUser);
	}

	@Override
	public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// pageNumber default starts from 0
//		Sort sort = Sort.by(sortBy);
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Users> page = usersRepo.findAll(pageable);
//		List<Users> users = page.getContent();
//
//		List<UserDto> dtoList =  users.stream().map(this::entityToDto).collect(Collectors.toList());
//		PageableResponse<UserDto> response = new PageableResponse<>();
//		response.setContent(dtoList);
//		response.setPageNumber(page.getNumber());
//		response.setPageSize(page.getSize());
//		response.setTotalElements(page.getTotalElements());
//		response.setTotalPages(page.getTotalPages());
//		response.setLastPage(page.isLast());

		PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
		return response;
	}

	@Override
	public UserDto getUserById(String userId) {
		Users user = usersRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("not found"));
		return entityToDto(user);
	}

	// this is custom method created by us and not available in repository
	@Override
	public UserDto getUserByIdEmail(String email) {
		Users user = usersRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("not doun"));
		return entityToDto(user);

	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		List<Users> users = usersRepo.findByNameContaining(keyword);
		List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
		return dtoList;
	}

	public UserDto entityToDto(Users savedUser) {
//		UserDto dto = new UserDto();
//		dto.setUserId(savedUser.getUserId());
//		dto.setName(savedUser.getName());
//		dto.setEmail(savedUser.getEmail());
//		dto.setPassword(savedUser.getPassword());
//		dto.setGender(savedUser.getGender());
//		dto.setAbout(savedUser.getAbout());
//		dto.setImageName(savedUser.getImageName()); 

		return mapper.map(savedUser, UserDto.class);
	}

	public Users dtoToEntity(UserDto userDto) {
//		Users user = new Users();
//		user.setUserId(userDto.getUserId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setGender(userDto.getGender());
//		user.setAbout(userDto.getAbout());
//		user.setImageName(userDto.getImageName());
//		return user;
		return mapper.map(userDto, Users.class);
	}

	@Override
	public void deleteUser(String userId) {
		// fetch user and delete file
		Users user = usersRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		String fullPath = imagePath + user.getImageName();

		try {
			Path path = Paths.get(fullPath);
			Files.delete(path);
		} catch (NoSuchFileException ex) {
			System.out.println("User image not found: " + fullPath);
			ex.printStackTrace();
		} catch (IOException e) {
			throw new RuntimeException("Error deleting image: " + fullPath, e);
		}

		usersRepo.delete(user);

	}

}
