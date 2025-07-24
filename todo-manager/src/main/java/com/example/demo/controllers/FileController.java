package com.example.demo.controllers;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

@RestController
@RequestMapping("/file")
public class FileController {

	Logger logger = LoggerFactory.getLogger(FileController.class);

	@PostMapping("/single")
	public String uploadSingle(@RequestParam("image") MultipartFile file) {
		logger.info("Name : {} ", file.getName());
		logger.info("ContentType : {}", file.getContentType());
		logger.info("File Size : {}", file.getSize());
//		file.getInputStream();
		return "files upload successfully";
	}

//	multiple file upload controller
	@PostMapping("/multiple")
	public String uploadMultiple(@RequestParam(value = "files", required = false) MultipartFile[] files) {
//		traversing array using foreach loop getting each file info 

		if (files == null || files.length == 0) {
			return "No files uploaded!";
		}
		Arrays.stream(files).forEach(file -> {
			logger.info("file name : {}", file.getOriginalFilename());
			logger.info("file type : {}", file.getContentType());
			System.out.println("+++++++++++++++++++++++++");
		});
		return "Multiple files uploaded successfully";

	}

//	serving image files in response
	@GetMapping("/serve-image")
	public void serveImageHandler(HttpServletResponse response) {
		try {
			InputStream fileInputStream = new FileInputStream("images/code.jpg");
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(fileInputStream, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
//	    If you wrote e.printstacktrace, it would give a compile-time error 
		// because Java is case-sensitive and no such method exists.
	}

}
