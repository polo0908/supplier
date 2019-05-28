package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.MessageCenter;

public interface MessageCenterDao extends Serializable {

	
	
	/**
	 * 插入消息
	 * @param messageCenter
	 */
	void insert(MessageCenter messageCenter);
	/**
	 * 查询客户的消息
	 * @param userid
	 * @return
	 */
	List<MessageCenter> queryMessageByUserId(@Param("salesId")String salesId,@Param("factoryId")String factoryId,
			@Param("start")Integer start,@Param("pageSize")Integer pageSize);
	
	
	/**
	 * 查询客户的消息
	 * @param userid
	 * @return
	 */
	List<MessageCenter> queryMessageByUserIdAdmin(@Param("factoryId")String factoryId,
			@Param("start")Integer start,@Param("pageSize")Integer pageSize);
	
	
	/**
	 * 根据Id查询
	 * @author polo
	 * 2017年5月16日
	 *
	 */
	MessageCenter queryById(Integer id);
	
	
	
	/**
	 * 查询消息数（管理员）
	 * @author polo
	 * 2017年5月15日
	 *
	 */
	int totalByAdmin(@Param("factoryId")String factoryId,@Param("messageType")Integer messageType);
	
	/**
	 * 查询消息数
	 * @param userid
	 * @param readStatus
	 * @return
	 */
	int total(@Param("salesId")String salesId,@Param("messageType")Integer messageType,@Param("factoryId")String factoryId);
	
	/**
	 * 查询每个类型消息未读数
	 * @author polo
	 * 2017年5月18日
	 *
	 */
	int totalByMessageType(@Param("messageCenterId")Integer messageCenterId,@Param("messageType")Integer messageType);
	
	/**
	 * 查询消息数
	 * @author polo
	 * 2017年5月19日
	 *
	 */
	int totalByMessage();
	
	
	/**
	 * 根据订单号查询消息
	 * @param orderId
	 * @return
	 */
    List<MessageCenter> queryMessageByOrderId(@Param("orderId")String orderId);
	
	
	/**
	 * 更新消息
	 * @author polo
	 * 2017年5月15日
	 *
	 */
	void updateMessage(MessageCenter messageCenter);
	
	
	/**
	 * 根据报价单id查询
	 * @author polo
	 * 2017年6月19日
	 *
	 */
	MessageCenter queryByQuotationId(@Param("quotationInfoId")Integer quotationInfoId);
	
	
	/**
	 * 查询总消息数（普通查询）
	 * @param salesId
	 * @param factoryId
	 * @return
	 */
	int totalMessageByFactoryId(@Param("salesId")String salesId,@Param("factoryId")String factoryId);
	
	/**
	 * 查询总消息数（管理员查询）
	 * @param salesId
	 * @param factoryId
	 * @return
	 */
	int totalMessageByFactoryIdAdmin(@Param("factoryId")String factoryId);
	
	
	
	
	/**
	 * 根据报价id查询未读消息数（客户未读消息）
	 * @param quotationInfoId
	 * @return
	 */
    int totalUnReadQuotationMessage(@Param("quotationInfoId")Integer quotationInfoId);
	
	
	
	
}
