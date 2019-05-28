package com.cbt.service;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.UserFactoryRelation;


public interface UserFactoryRelationService extends Serializable {
	/**
	 * 单个factoryId查询
	 * @param orderId
	 * @return
	 */
	public List<UserFactoryRelation> queryByFactoryId(String factoryId);
	
	/**
	 * 单个userid查询
	 * @param orderId
	 * @return
	 */
	public List<UserFactoryRelation> queryByUserid(String userid);
	
	
	 /**
	  * 插入工厂客户关联数据
	  * @param factoryInfo
	  */
	 public void insert(UserFactoryRelation factoryUserRelation);
	 
	 
	 /**
	  * 查询用户工厂数
	  * @return
	  */
	 public int totalFactory(String userid);
}
