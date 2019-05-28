package com.cbt.entity;

import java.io.Serializable;
import java.util.List;

public class ReadSmartWatchExcelVO implements Serializable {

	
	/**
	 * 报价单解析对象
	 * @fieldName serialVersionUID
	 * @fieldType long
	 * @Description 
	 */
	private static final long serialVersionUID = 1L;
	private String pid;                //产品编号
	private String title;              //标题
	private String keyword;            //关键字
	private String function;           //功能
	private String watchcaseMaterial;  //表壳材质
	private String strapMaterial;      //表带材质
	private String system;            //操作系统
	private String speciality;        //特性
	private String brand;             //品牌
	private String pic;               //主图
	private String type;              //产品类型
	private String[] otherPic;          //其他图片
	private String price;             //产品价格
	
	
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String[] getOtherPic() {
		return otherPic;
	}
	public void setOtherPic(String[] otherPic) {
		this.otherPic = otherPic;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getWatchcaseMaterial() {
		return watchcaseMaterial;
	}
	public void setWatchcaseMaterial(String watchcaseMaterial) {
		this.watchcaseMaterial = watchcaseMaterial;
	}
	public String getStrapMaterial() {
		return strapMaterial;
	}
	public void setStrapMaterial(String strapMaterial) {
		this.strapMaterial = strapMaterial;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ReadSmartWatchExcelVO [pid=" + pid + ", title=" + title
				+ ", keyword=" + keyword + ", function=" + function
				+ ", watchcaseMaterial=" + watchcaseMaterial
				+ ", strapMaterial=" + strapMaterial + ", system=" + system
				+ ", speciality=" + speciality + ", brand=" + brand + ", pic="
				+ pic + ", type=" + type + ", otherPic=" + otherPic
				+ ", price=" + price + "]";
	}
	
	
	
	
}
