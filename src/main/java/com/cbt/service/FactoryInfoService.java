package com.cbt.service;

import java.io.Serializable;

import com.cbt.entity.FactoryInfo;

public interface FactoryInfoService extends Serializable {
           
	
	/**
	 * 查询id最大值
	 * @return
	 */
	public Integer queryMaxId();
	
	
	/**
	 * 单个factoryId查询
	 * @param orderId
	 * @return
	 */
	public FactoryInfo queryByFactoryId(String factoryId);
	
	
	 /**
	  * 插入工厂数据 
	  * @param factoryInfo
	  */
	public void insert(FactoryInfo factoryInfo);
	
	
	 /**
	  * 更新工厂信息
	  * @param factoryInfo
	  */
	 public void updateFactoryInfo(FactoryInfo factoryInfo);
}
