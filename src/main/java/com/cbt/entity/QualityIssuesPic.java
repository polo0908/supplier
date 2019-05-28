package com.cbt.entity;

import java.io.Serializable;

public class QualityIssuesPic implements Serializable {
  
	 private Integer id;
	 private Integer orderMessageId;
	 private String picturePath;              //图片路径
	 private String picturePathCompress;      //图片压缩后路径
	 
	 
	 
	 
	public String getPicturePathCompress() {
		return picturePathCompress;
	}
	public void setPicturePathCompress(String picturePathCompress) {
		this.picturePathCompress = picturePathCompress;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderMessageId() {
		return orderMessageId;
	}
	public void setOrderMessageId(Integer orderMessageId) {
		this.orderMessageId = orderMessageId;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	@Override
	public String toString() {
		return "QualityIssuesPic [id=" + id + ", orderMessageId="
				+ orderMessageId + ", picturePath=" + picturePath
				+ ", picturePathCompress=" + picturePathCompress + "]";
	}
	 
	 
	 
	 
}
