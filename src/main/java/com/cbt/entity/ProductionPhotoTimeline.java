package com.cbt.entity;

import java.io.Serializable;

public class ProductionPhotoTimeline implements Serializable {

	
	/**
	 * @fieldName serialVersionUID
	 * @fieldType long
	 * @Description TODO
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String orderId;       //订单id
	private String photoName;     //图片名
	private String uploadDate;    //上传日期
	private String remarks;       //注释
	private String photoPath;     //图片路径
	private String photoPathCompress;  //压缩图片路径
	
	//增加文档类型支持
	//增加录入销售 
	//2017/12/07
	private String documentPath;    //文档路径
	private Integer isDocument;     //是否文档类型（0：不是 1：是）
	private String inputSales;      //录入销售
	
	
	
	
	public String getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
	public Integer getIsDocument() {
		return isDocument;
	}
	public void setIsDocument(Integer isDocument) {
		this.isDocument = isDocument;
	}
	public String getInputSales() {
		return inputSales;
	}
	public void setInputSales(String inputSales) {
		this.inputSales = inputSales;
	}
	public String getPhotoPathCompress() {
		return photoPathCompress;
	}
	public void setPhotoPathCompress(String photoPathCompress) {
		this.photoPathCompress = photoPathCompress;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "ProductionPhotoTimeline [id=" + id + ", orderId=" + orderId
				+ ", photoName=" + photoName + ", uploadDate=" + uploadDate
				+ ", remarks=" + remarks + ", photoPath=" + photoPath
				+ ", photoPathCompress=" + photoPathCompress
				+ ", documentPath=" + documentPath + ", isDocument="
				+ isDocument + ", inputSales=" + inputSales + "]";
	}
	
	
	
	
}
