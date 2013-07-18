package com.kmb.origami.model;

public class SingleItem {
	private int mId;
	private int mMaxStage;

	public SingleItem(int id, int maxStage) {
		this.mId = id;
		this.mMaxStage = maxStage;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}

	public int getMaxStage() {
		return mMaxStage;
	}

	public void setMaxStage(int maxStage) {
		this.mMaxStage = maxStage;
	}

}