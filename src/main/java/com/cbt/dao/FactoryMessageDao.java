package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.FactoryMessage;

public interface FactoryMessageDao extends Serializable {

	
	/**
	 * 插入工厂回复
	 * @author polo
	 * 2017年5月17日
	 *
	 */
	void insert(FactoryMessage factoryMessage);
	
	
	
	/**
	 * 根据订单消息id查询
	 * @author polo
	 * 2017年5月17日
	 *
	 */
	List<FactoryMessage> queryByOrderMessageId(Integer orderMessageId);
		
	
}
