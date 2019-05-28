package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.FactoryUserRelation;

public interface FactoryUserRelationDao extends Serializable {

	
	
	/**
	 * 单个factoryId查询
	 * @param orderId
	 * @return
	 */
	List<FactoryUserRelation> queryByFactoryId(String factoryId);
	
	/**
	 * 单个userid查询
	 * @param orderId
	 * @return
	 */
	List<FactoryUserRelation> queryByUserid(String userid);
	
	
	 /**
	  * 插入工厂客户关联数据 
	  * @param factoryInfo
	  */
	 void insert(FactoryUserRelation factoryUserRelation);
	 
	 
	 /**
	  * 根据工厂Id和用户Id查询
	  * @param factoryId
	  * @param userid
	  * @return
	  */
	 FactoryUserRelation queryByFactoryIdAndUserid(@Param("factoryId")String factoryId,@Param("userid")String userid);
	 
	 /**
	  * 插入工厂客户关联数据 
	  * @param factoryInfo
	  */
	 void updateRemark(FactoryUserRelation factoryUserRelation);
	 
	 
	 
	 /**
	  * 查询数据是否存在  	
	  * @param factoryId
	  * @param userid
	  * @return
	  */
	 int queryCount(@Param("factoryId")String factoryId,@Param("userid")String userid);
	 
	 
	 
	 
  	/**
  	 * 批量插入关联表
  	 */	
   	void insertBatch(List<Object> list);
}
