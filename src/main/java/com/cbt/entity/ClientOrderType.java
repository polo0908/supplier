package com.cbt.entity;

import java.io.Serializable;

public class ClientOrderType implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String orderType;  //订单类型
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	@Override
	public String toString() {
		return "ClientOrderType [id=" + id + ", orderType=" + orderType + "]";
	}
	
	
	
	
}
