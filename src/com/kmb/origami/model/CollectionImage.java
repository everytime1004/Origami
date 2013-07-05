package com.kmb.origami.model;

import android.graphics.Bitmap;

public class CollectionImage {
	private int id;
	private Bitmap image;

	public CollectionImage(int id, Bitmap image) {
		this.id = id;
		this.image = image;
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
}