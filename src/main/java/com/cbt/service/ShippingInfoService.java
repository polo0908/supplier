package com.cbt.service;

import java.util.List;

import com.cbt.entity.ShippingInfo;

public interface ShippingInfoService {

	/**
	 * 批量插入用户
	 */	
	public void insertShippingInfo(List<Object> list);
   
	/**
	 * 根据客户名查询
	 * @param userid
	 * @return
	 */	  
	public ShippingInfo queryByUserId(String userid);
	
	/**
	 * 根据客户名查询
	 * @param userid
	 * @return
	 */	  
	public void updateShippingInfo(ShippingInfo shippingInfo);
	
	/**
	 * 插入用户
	 */	
	public void insertInfo(ShippingInfo shippingInfo);
}
