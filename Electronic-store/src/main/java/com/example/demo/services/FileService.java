package com.example.demo.services;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	//multipe file upload and where it will store at what path
	String uploadFile(MultipartFile file, String path);
	
	InputStream getResource(String path, String name) throws FileNotFoundException;
	
	//now write implementation of both this method 

}

//writing two methods here one for file upload 