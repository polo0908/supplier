package com.cbt.dao;

import java.util.List;

import com.cbt.entity.ShippingInfo;
import com.cbt.entity.User;

public interface ShippingInfoDao {
  
	/**
	 * 批量插入用户
	 */	
	void insertShippingInfo(List<Object> list);
	
	/**
	 * 插入用户
	 */	
	void insertInfo(ShippingInfo shippingInfo);
   
	/**
	 * 根据客户名查询
	 * @param userid
	 * @return
	 */	  
	ShippingInfo queryByUserId(String userid);
	
	/**
	 * 根据客户名查询
	 * @param userid
	 * @return
	 */	  
	void updateShippingInfo(ShippingInfo shippingInfo);
	
	
}
