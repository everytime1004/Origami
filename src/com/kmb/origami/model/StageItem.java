package com.kmb.origami.model;

public class StageItem {
	private int id;
	private int maxStage;
	private String mSubject;

	public StageItem(int id, int maxStage, String mSubject) {
		this.id = id;
		this.maxStage = maxStage;
		this.mSubject = mSubject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getMaxStage(){
		return maxStage;
	}
	
	public void setMaxStage(int maxStage){
		this.maxStage = maxStage;
	}
	
	public String getSubject(){
		return mSubject;
	}
	
	public void setSubject(String mSubject){
		this.mSubject = mSubject;
	}

}
