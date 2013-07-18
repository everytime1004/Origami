package com.kmb.origami.model;

import android.graphics.Bitmap;

public class CollectionImage {
	private int id;
	private Bitmap image;
	private int lastIndex;

	public CollectionImage(int id, Bitmap image, int lastIndex) {
		this.id = id;
		this.image = image;
		this.lastIndex = lastIndex;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	public void setLastIndex(int lastIndex){
		this.lastIndex = lastIndex;
	}
	
	public int getLastIndex(){
		return lastIndex;
	}
}