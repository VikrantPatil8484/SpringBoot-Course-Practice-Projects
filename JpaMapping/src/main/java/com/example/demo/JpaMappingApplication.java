package com.example.demo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.repositories.ProductRepo;
import com.example.demo.repositories.StudentRepository;

@SpringBootApplication
public class JpaMappingApplication implements CommandLineRunner{
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	Logger logger = LoggerFactory.getLogger(JpaMappingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JpaMappingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
//		Student student = new Student();
//		student.setStudentName("vkrant patil");
//		student.setAbout("I am software developer");
//		student.setStudentId(15);
		
//		Laptop laptop = new Laptop();
//		laptop.setModelNumber("123");
//		laptop.setBrand("DELL");
//		laptop.setLaptopId(1234);
//		laptop.setStudent(student);
		
		
//		important to set 
//		student.setLaptop(laptop);
//		laptop.setStudent(student);
		
//		manually to save laptop with the help of laptop repo
//		or else use cascade property in student so when we save student it laptop is also get save
//		Student save = studentRepository.save(student);
//		logger.info("student info : {}", save);
		
//		Student stu = studentRepository.findById(13);
//		logger.info("student:{}",stu.getStudentName());
		
		
//		onetomany
//		Student student = new Student();
//		student.setStudentName("vkrant patil");
//		student.setAbout("I am software developer");
//		student.setStudentId(15);
//		
//		Address a1 = new Address();
//		a1.setAddressId(122);
//		a1.setStreet("234/234");
//		a1.setStreet("MUM");
//		a1.setCountry("IND");
//		a1.setStudent(student);
//		
//		Address a2 = new Address();
//		a2.setAddressId(132);
//		a2.setStreet("236/266");
//		a2.setStreet("PUN");
//		a2.setCountry("IND");
//		a2.setStudent(student);
//		
//		List<Address> addList = new ArrayList<>();
//		addList.add(a1);
//		addList.add(a2);
//		
//		student.setAddressList(addList);
//		
//		Student save = studentRepository.save(student);
//		logger.info("student address info saved successfully");
		
		
//		ManyToMnay realtionship
		
		//creating product
		Product product1 = new Product();
		product1.setId("pid1");
		product1.setProductName("Iphone 15 pro");
		
		Product product2 = new Product();
		product2.setId("pid2");
		product2.setProductName("vivo 15 pro");
		
		Product product3 = new Product();
		product3.setId("pid3");
		product3.setProductName("samsung 15 pro");
		
		Category category1 = new Category();
		category1.setcId("cid1");
		category1.setTitle("Electronics");
		
		
		Category category2 = new Category();
		category2.setcId("cid2");
		category2.setTitle("Mobile Phone");
		
		
		List<Product> category1Products = category1.getProduct();
		category1Products.add(product1);
		category1Products.add(product2);
		category1Products.add(product3);
		
		
		List<Product> category2Products = category2.getProduct();
		category2Products.add(product1);
		category2Products.add(product2);
		
		categoryRepo.save(category1);
		categoryRepo.save(category2); //as we have used cascade a;; so afte saving category product will be saved automatically 
		
		
		
		
		
	}

}
