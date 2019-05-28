package com.cbt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.FactoryMessage;
import com.cbt.entity.OrderMessage;

public interface OrderMessageDao {

	
	
	
	/**
	 * 插入消息
	 * @param orderMessage
	 */
	void insert(OrderMessage orderMessage);
	
	

	/**
	 * 根据消息中心ID查询
	 * @author polo
	 * 2017年5月16日
	 *
	 */
	List<OrderMessage> queryByMessageCenterId(Integer messageCenterId);
	/**
	 * 根据消息中心ID查询客户消息
	 * @author polo
	 * 2017年5月19日
	 *
	 */
	List<OrderMessage> queryByMessageCenterIdAndType(@Param("messageCenterId")Integer messageCenterId,@Param("customerOrFactory")Integer customerOrFactory);
	
	
	
	/**
	 * 批量更新读取状态
	 * @author polo
	 * 2017年5月19日
	 *
	 */
	void updateReadStatus(List<OrderMessage> orderMessages);
	
	
	/**
	 * 根据报价单id查询消息
	 * @param quotationId
	 * @return
	 */
	List<OrderMessage> queryByQuotationId(Integer quotationId);
	
	
	/**
	 * 根据id查询消息
	 * @param id
	 * @return
	 */
	OrderMessage queryById(Integer id);
	
}
