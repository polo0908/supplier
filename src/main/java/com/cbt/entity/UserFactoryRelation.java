package com.cbt.entity;

import java.io.Serializable;

public class UserFactoryRelation implements Serializable {

	
	private Integer id;          
	private String factoryId;    //工厂ID
	private String userid;       //用户ID
	private String userFactoryRemark;  //客户添加工厂备注
	
	
	public String getUserFactoryRemark() {
		return userFactoryRemark;
	}
	public void setUserFactoryRemark(String userFactoryRemark) {
		this.userFactoryRemark = userFactoryRemark;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	@Override
	public String toString() {
		return "UserFactoryRelation [id=" + id + ", factoryId=" + factoryId
				+ ", userid=" + userid + ", userFactoryRemark="
				+ userFactoryRemark + "]";
	}
	
	
}
