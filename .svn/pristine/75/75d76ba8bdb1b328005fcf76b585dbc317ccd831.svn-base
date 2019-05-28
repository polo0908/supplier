package com.cbt.service;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.FactoryUserRelation;


public interface FactoryUserRelationService extends Serializable {
	/**
	 * 单个factoryId查询
	 * @param orderId
	 * @return
	 */
	public List<FactoryUserRelation> queryByFactoryId(String factoryId);
	
	/**
	 * 单个userid查询
	 * @param orderId
	 * @return
	 */
	public List<FactoryUserRelation> queryByUserid(String userid);
	
	
	 /**
	  * 插入工厂客户关联数据
	  * @param factoryInfo
	  */
	 public void insert(FactoryUserRelation factoryUserRelation);
	 
	 
	 /**
	  * 根据工厂Id和用户Id查询
	  * @param factoryId
	  * @param userid
	  * @return
	  */
	 public FactoryUserRelation queryByFactoryIdAndUserid(String factoryId,String userid);
	 
	 
	 /**
	  * 插入工厂客户关联数据 
	  * @param factoryInfo
	  */
	 public void updateRemark(FactoryUserRelation factoryUserRelation);
	 
	 
	 /**
	  * 查询数据是否存在  	
	  * @param factoryId
	  * @param userid
	  * @return
	  */
	 public int queryCount(@Param("factoryId")String factoryId,@Param("userid")String userid);
}
