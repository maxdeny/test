package com.beatle.lg.carriage.data;

import java.io.Serializable;

public class ImageType implements Serializable{
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	private String imgUrl;
	
	private String imgType;
	
	public ImageType(String imgUrl, String imgType) {
		super();
		this.imgUrl = imgUrl;
		this.imgType = imgType;
	}

	
	
	

}
