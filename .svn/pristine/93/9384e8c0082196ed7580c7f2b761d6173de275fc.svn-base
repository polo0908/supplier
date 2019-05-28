package com.cbt.dao;

import java.io.Serializable;

import com.cbt.entity.FactoryInfo;

public interface FactoryInfoDao extends Serializable {

	/**
	 * 查询id最大值
	 * @return
	 */
	Integer queryMaxId();
	/**
	 * 单个factoryId查询
	 * @param orderId
	 * @return
	 */
	FactoryInfo queryByFactoryId(String factoryId);
	
	
	 /**
	  * 插入工厂数据 
	  * @param factoryInfo
	  */
	 void insert(FactoryInfo factoryInfo);
	 
	 
	 /**
	  * 更新工厂信息
	  * @param factoryInfo
	  */
	 void updateFactoryInfo(FactoryInfo factoryInfo);
}
