package com.example.demo.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exceptions.BadApiRequest;
import com.example.demo.services.FileService;

@Service
public class FileServiceImpl implements FileService{
	
	private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

	@Override
	public String uploadFile(MultipartFile file, String path) {
	    // Step 1: Get original filename
	    String originalFilename = file.getOriginalFilename();
	    logger.info("Filename: {}", originalFilename);

	    // Step 2: Generate unique filename
	    String filename = UUID.randomUUID().toString();

	    // Step 3: Extract extension
	    String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
	    String fileNameWithExtension = filename + extension;

	    // Step 4: Check extension type
	    if (!extension.equalsIgnoreCase(".png") &&
	        !extension.equalsIgnoreCase(".jpg") &&
	        !extension.equalsIgnoreCase(".jpeg")) {
	        throw new RuntimeException("Only PNG, JPG, and JPEG files are allowed");
	    }

	    // Step 5: Build full path
	    String fullPathWithFileName = path + File.separator + fileNameWithExtension;
	    // Step 6: Create folder if not exists
	    try {
	        File folder = new File(path);
	        if (!folder.exists()) {
	            folder.mkdirs();
	        }

	        // Step 7: Copy file to target location
	        Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

	        // Step 8: Return generated file name
	        return fileNameWithExtension;

	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new BadApiRequest("File upload failed");
	    }
	}

	

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
		
		String fullPath = path+File.separator + name;
		InputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}
	
}
