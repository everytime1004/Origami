package com.kmb.origami.model;

public class Post {
	private int id;
	private String title;
	private String descirption;
	private String updated_time;
	private String author;
	private String imageURL;

	public Post(int id, String title, String descirption, String updated_time, String author, String imageURL) {
		this.id = id;
		this.title = title;
		this.descirption = descirption;
		this.updated_time = updated_time;
		this.author = author;
		this.imageURL = imageURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription(){
		return descirption;
	}
	
	public void setDescription(String descirption){
		this.descirption = descirption;
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
	
	public String getImageURL(){
		return imageURL;
	}
	
	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}
}
