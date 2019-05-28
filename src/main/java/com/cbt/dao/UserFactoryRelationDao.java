package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.UserFactoryRelation;

public interface UserFactoryRelationDao extends Serializable {

	
	
	/**
	 * 单个factoryId查询
	 * @param orderId
	 * @return
	 */
	List<UserFactoryRelation> queryByFactoryId(String factoryId);
	
	/**
	 * 单个userid查询
	 * @param orderId
	 * @return
	 */
	List<UserFactoryRelation> queryByUserid(String userid);
	
	
	 /**
	  * 插入工厂客户关联数据 
	  * @param factoryInfo
	  */
	 void insert(UserFactoryRelation factoryUserRelation);
	 
	 /**
	  * 查询用户工厂数
	  * @return
	  */
	 int totalFactory(String userid);
	 
	 
  	/**
  	 * 批量插入关联表
  	 */	
   	void insertBatch(List<Object> list);
   	

   	
} 
