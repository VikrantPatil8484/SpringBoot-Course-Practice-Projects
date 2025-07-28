package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "jpa-student")
public class Student {

	@Id
	private int studentId;
	private String studentName;
	private String about;

	// one student can have one laptop only so laptop id will be store in this
	// student table using onetoone annotation
	@OneToOne(mappedBy = "student", cascade= CascadeType.ALL)
	private Laptop laptop;
	
	//many address
	@OneToMany(mappedBy="student", cascade= CascadeType.ALL)
	private List<Address> addressList = new ArrayList<>();	

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Laptop getLaptop() {
		return laptop;
	}

	public void setLaptop(Laptop laptop) {
		this.laptop = laptop;
	}

	public Student(int studentId, String studentName, String about, Laptop laptop) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.about = about;
		this.laptop = laptop;
	}

	public Student() {
		// TODO Auto-generated constructor stub
	}
	public List<Address> getAddressList() {
	    return addressList;
	}

	public void setAddressList(List<Address> addressList) {
	    this.addressList = addressList;
	}


	// if we get laptop then we can know the student this is unidirectional
	// if we get student then we can not know the laptop so this is bidirectional we
	// need to do
	

}


//1. Student to Laptop - OneToOne relationship one student can have one laptop
//2. Student to address - OneToMany relationship one student can have multiple address
//3. Product to Category - ManyToMany relationships one category can have mayn product and one product can be in two or more categories

