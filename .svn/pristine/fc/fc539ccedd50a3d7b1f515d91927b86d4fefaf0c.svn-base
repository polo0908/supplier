package com.cbt.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.MessageLogTabMapper;
import com.cbt.entity.MessageLogTab;
import com.cbt.service.MessageLogTabService;
@Service
public class MessageLogTabServiceImpl implements MessageLogTabService {

	@Resource
	private MessageLogTabMapper messageLogTabMapper;
	
	@Override
	public int insertSelective(MessageLogTab record) {
		return messageLogTabMapper.insertSelective(record);
	}

}
