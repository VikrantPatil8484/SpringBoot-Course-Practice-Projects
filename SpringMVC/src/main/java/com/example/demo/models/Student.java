package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Student {

//	@JsonIgnore
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	while consume : json include : setter : @JSonProperty
//	while produce : json exclude : getter : JsonIgnore
	
	@Override
	public String toString() {
		return "Student [name=" + name + ", empId=" + empId + ", phone=" + phone + ", dept=" + dept + "]";
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	private int empId;
	private String phone;
	private String dept;

}
