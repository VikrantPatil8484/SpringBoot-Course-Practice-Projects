package com.example.demo.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Todo {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public Todo() {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.status = status;
		this.addedDate = addedDate;
		this.toDoDate = toDoDate;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Date getToDoDate() {
		return toDoDate;
	}

	public void setToDoDate(Date toDoDate) {
		this.toDoDate = toDoDate;
	}

	

	@Override
	public String toString() {
		return "Todo [id=" + id + ", title=" + title + ", content=" + content + ", status=" + status + ", addedDate="
				+ addedDate + ", toDoDate=" + toDoDate + "]";
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String title;
	private String content;
	private String status;

	private Date addedDate;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date toDoDate;

}
