package com.kmb.origami.model;

public class Post {
	private int id;
	private String title;
	private String updated_time;
	private String author;

	public Post(int id, String title, String updated_time, String author) {
		this.id = id;
		this.title = title;
		this.updated_time = updated_time;
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUpdated_time(){
		return updated_time;
	}
	
	public void setUpdated_time(String updated_time){
		this.updated_time = updated_time;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
}
