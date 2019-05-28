package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cbt.dao.FactoryMessageDao;
import com.cbt.entity.FactoryMessage;
import com.cbt.service.FactoryMessageService;

@Service
public class FactoryMessageServiceImpl implements FactoryMessageService {
 
	@Resource
	private FactoryMessageDao factoryMessageDao;
	
	@Transactional
	@Override
	public FactoryMessage insert(FactoryMessage factoryMessage)throws Exception{
		
		factoryMessageDao.insert(factoryMessage);
		return factoryMessage;
	}

}
