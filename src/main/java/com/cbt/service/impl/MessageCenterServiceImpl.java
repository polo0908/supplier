package com.cbt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cbt.dao.MessageCenterDao;
import com.cbt.dao.OrderMessageDao;
import com.cbt.dao.QualityIssuesPicDao;
import com.cbt.entity.MessageCenter;
import com.cbt.entity.OrderMessage;
import com.cbt.service.MessageCenterService;

@Service
public class MessageCenterServiceImpl implements MessageCenterService {
    
	private static final Integer PERMISSION = 1;
	private static final Integer PUSH_MESSAGE = 1;
	private static final Integer QUALITY_MESSAGE = 2;
	private static final Integer OTHER_MESSAGE = 3;
	private static final Integer QUOTATION_MESSAGE = 4;
	
	@Resource
	private MessageCenterDao messageCenterDao;
	@Resource
	private QualityIssuesPicDao qualityIssuesPicDao;
	@Resource
	private OrderMessageDao orderMessageDao;

	@Override
	public List<MessageCenter> queryMessageByUserId(String salesId,String factoryId,Integer permission,Integer start,Integer pageSize) {
		
		StringUtils.isBlank(salesId);
		StringUtils.isBlank(factoryId);		
		List<MessageCenter> list = new ArrayList<MessageCenter>();
		if(permission == PERMISSION){
			list = messageCenterDao.queryMessageByUserIdAdmin(factoryId, start, pageSize);
		}else{
			list = messageCenterDao.queryMessageByUserId(salesId, factoryId, start, pageSize);
		}
		
		return list;
	}

	
	@Override
	public int total(String salesId,String factoryId,Integer permission)throws Exception{
		
		StringUtils.isBlank(salesId);
		StringUtils.isBlank(factoryId);
		int amount = 0; 
		if(permission == PERMISSION){
			amount = messageCenterDao.totalByAdmin(factoryId, PUSH_MESSAGE) + messageCenterDao.totalByAdmin(factoryId, QUALITY_MESSAGE) + messageCenterDao.totalByAdmin(factoryId, OTHER_MESSAGE) + messageCenterDao.totalByAdmin(factoryId, QUOTATION_MESSAGE);
			
		}else{
			amount = messageCenterDao.total(salesId, PUSH_MESSAGE, factoryId) + messageCenterDao.total(salesId, QUALITY_MESSAGE, factoryId) + messageCenterDao.total(salesId, OTHER_MESSAGE,factoryId) + messageCenterDao.total(salesId, QUOTATION_MESSAGE,factoryId);
		}
		return amount;
	}
	
	@Transactional
	@Override
	public void updateMessage(MessageCenter messageCenter) {
		messageCenterDao.updateMessage(messageCenter);		
		
	}


	@Override
	public int totalByMessageType(Integer messageCenterId, Integer messageType) {
		return messageCenterDao.totalByMessageType(messageCenterId, messageType);
	}


	@Override
	public int totalByMessage() {
		return messageCenterDao.totalByMessage();
	}
	

	@Transactional
	@Override
	public OrderMessage insertQuotaionMessage(MessageCenter messageCenter,OrderMessage orderMessage) throws Exception {
		MessageCenter messageCenter2 = messageCenterDao.queryByQuotationId(messageCenter.getQuotationInfoId());
		if(messageCenter2 == null || "".equals(messageCenter2)){
			messageCenterDao.insert(messageCenter);
			orderMessage.setMessageCenterId(messageCenter.getId());
		    orderMessageDao.insert(orderMessage);
		}else{
			orderMessage.setMessageCenterId(messageCenter2.getId());
			orderMessageDao.insert(orderMessage);
		}
		
		return orderMessage;
	}


	@Override
	public int totalMessageByFactoryId(String salesId, String factoryId) {
		return messageCenterDao.totalMessageByFactoryId(salesId, factoryId);
	}


	@Override
	public int totalMessageByFactoryIdAdmin(String factoryId) {
		return messageCenterDao.totalMessageByFactoryIdAdmin(factoryId);
	}


	@Override
	public int totalUnReadQuotationMessage(Integer quotationInfoId) {
		return messageCenterDao.totalUnReadQuotationMessage(quotationInfoId);
	}


	@Override
	public List<MessageCenter> queryMessageByOrderId(String orderId) {
		return messageCenterDao.queryMessageByOrderId(orderId);
	}




	




}
