package com.cbt.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cbt.entity.OrderMessage;

public interface OrderMessageService extends Serializable {

	
	
	/**
	 * 根据消息中心ID查询
	 * @author polo
	 * 2017年5月16日
	 *
	 */
	public Map<String,Object> updateOrderMessage(Integer messageCenterId)throws Exception;
	
	
	
	/**
	 * 根据消息中心ID查询
	 * @author polo
	 * 2017年5月16日
	 *
	 */
	public List<OrderMessage> queryByMessageCenterId(Integer messageCenterId);
	
	
	
	
	
	/**
	 * 插入消息
	 * @param orderMessage
	 */
	public OrderMessage insert(HttpServletRequest request,OrderMessage orderMessage)throws Exception;
	
	/**
	 * 根据id查询消息
	 * @param id
	 * @return
	 */
	public OrderMessage queryById(Integer id);
	
	/**
	 * 根据报价单id查询消息
	 * @param quotationId
	 * @return
	 */
	public List<OrderMessage> queryByQuotationInfoId(Integer quotationId);
	
	
	
	/**
	 * 批量更新读取状态
	 * @author polo
	 * 2017年5月19日
	 *
	 */
	public void updateReadStatus(List<OrderMessage> orderMessages);
}
