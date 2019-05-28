package com.cbt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.cbt.dao.BackUserDao;
import com.cbt.dao.FactoryMessageDao;
import com.cbt.dao.MessageCenterDao;
import com.cbt.dao.OrderMessageDao;
import com.cbt.dao.QualityIssuesPicDao;
import com.cbt.entity.BackUser;
import com.cbt.entity.MessageCenter;
import com.cbt.entity.OrderMessage;
import com.cbt.entity.QualityIssuesPic;
import com.cbt.service.OrderMessageService;
import com.cbt.util.DateFormat;
import com.cbt.util.WebCookie;

@Service
public class OrderMessageServiceImpl implements OrderMessageService {

	
	 private static final int READ_STATUS = 1;
	 private static final int CUSTOMER_MESSAGE = 1;
	 private static final int HAVE_PIC = 1;
	 
	@Resource
	private OrderMessageDao orderMessageDao;
	@Resource
	private MessageCenterDao messageCenterDao;
	@Resource
	private FactoryMessageDao factoryMessageDao;
	@Resource
	private QualityIssuesPicDao qualityIssuesPicDao;
	@Resource
	private BackUserDao backUserDao;
	
	@Transactional
	@Override
	public Map<String,Object> updateOrderMessage(Integer messageCenterId) throws Exception{
				
		List<OrderMessage> orderMessages = orderMessageDao.queryByMessageCenterIdAndType(messageCenterId,CUSTOMER_MESSAGE);
		MessageCenter messageCenter = messageCenterDao.queryById(messageCenterId);
		List<OrderMessage> os = orderMessageDao.queryByMessageCenterId(messageCenterId);
		
		List<List<QualityIssuesPic>> pics = new ArrayList<List<QualityIssuesPic>>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(orderMessages.size() != 0){
		for (OrderMessage orderMessage : orderMessages) {
			orderMessage.setReadStatus(READ_STATUS);
			orderMessage.setMessageReadTime(DateFormat.format());		
		}
		orderMessageDao.updateReadStatus(orderMessages);	
	  }
		
		
		//获取全部消息、图片
		for (OrderMessage orderMessage : os) {
			if(orderMessage.getPicStatus() == HAVE_PIC){
				List<QualityIssuesPic> pic = qualityIssuesPicDao.queryByOrderMessageId(orderMessage.getId());
				pics.add(pic);
			}
		}
				
		map.put("messageCenter", messageCenter);
		map.put("orderMessages", os);
		map.put("pics", pics);
		
		
		return map;
	}

	@Override
	public List<OrderMessage> queryByMessageCenterId(Integer messageCenterId) {
		return orderMessageDao.queryByMessageCenterId(messageCenterId);
	}

	@Transactional
	@Override
	public OrderMessage insert(HttpServletRequest request,OrderMessage orderMessage)throws Exception {
		orderMessageDao.insert(orderMessage);
		String userName = WebCookie.getUserName(request);
		orderMessage.setUsername(userName);
		return orderMessage;
	}

	@Override
	public List<OrderMessage> queryByQuotationInfoId(Integer quotationId) {
		return orderMessageDao.queryByQuotationId(quotationId);
	}

	@Override
	public OrderMessage queryById(Integer id) {
		return orderMessageDao.queryById(id);
	}

	@Override
	public void updateReadStatus(List<OrderMessage> orderMessages) {
		if(orderMessages.size() != 0){
		orderMessageDao.updateReadStatus(orderMessages);
		}
	}
	
	
	

}
