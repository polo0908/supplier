package com.cbt.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.MessageCenter;
import com.cbt.entity.OrderMessage;

public interface MessageCenterService {

	
	
	
	/**
	 * 查询客户的消息
	 * @param userid
	 * @return
	 */
	public List<MessageCenter> queryMessageByUserId(String salesId,String factoryId,Integer permission,Integer start,Integer pageSize);

	
	
	/**
	 * 查询所有未读消息数
	 * @param userid
	 * @param readStatus
	 * @return
	 */
	public int total(String salesId,String factoryId,Integer permission)throws Exception;
	
	/**
	 * 查询消息数
	 * @author polo
	 * 2017年5月19日
	 *
	 */
	public int totalByMessage();
	
	
	/**
	 * 更新消息
	 * @author polo
	 * 2017年5月15日
	 *
	 */
	public void updateMessage(MessageCenter messageCenter);
	
	
	/**
	 * 查询每个类型消息未读数
	 * @author polo
	 * 2017年5月18日
	 *
	 */
	public int totalByMessageType(Integer messageCenterId,Integer messageType);
	

	/**
	 * 插入报价单消息
	 * @param messageCenter
	 * @param quotationMessage
	 * @throws Exception
	 */
	public OrderMessage insertQuotaionMessage(MessageCenter messageCenter,OrderMessage orderMessage)throws Exception;
	
	
	/**
	 * 查询总消息数（普通查询）
	 * @param salesId
	 * @param factoryId
	 * @return
	 */
	public int totalMessageByFactoryId(String salesId,String factoryId);
	
	/**
	 * 查询总消息数（管理员查询）
	 * @param salesId
	 * @param factoryId
	 * @return
	 */
	public int totalMessageByFactoryIdAdmin(String factoryId);
	
	
	/**
	 * 根据报价id查询未读消息数（客户未读消息）
	 * @param quotationInfoId
	 * @return
	 */
    public int totalUnReadQuotationMessage(@Param("quotationInfoId")Integer quotationInfoId);
    
    
    
	/**
	 * 根据订单号查询消息
	 * @param orderId
	 * @return
	 */
   public List<MessageCenter> queryMessageByOrderId(String orderId);
	
}
