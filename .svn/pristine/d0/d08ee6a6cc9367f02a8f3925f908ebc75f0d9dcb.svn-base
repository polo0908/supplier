package com.cbt.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.FactoryInfoDao;
import com.cbt.entity.FactoryInfo;
import com.cbt.service.FactoryInfoService;

@Service
public class FactoryInfoServiceImpl implements FactoryInfoService {

	@Resource
	private FactoryInfoDao factoryInfoDao;
	
	public FactoryInfo queryByFactoryId(String factoryId) {
		return factoryInfoDao.queryByFactoryId(factoryId);
	}
    
	
	@Transactional
	@Override
	public void insert(FactoryInfo factoryInfo) {
		factoryInfoDao.insert(factoryInfo);

	}

	@Override
	public Integer queryMaxId() {		
		return factoryInfoDao.queryMaxId();
	}

	@Transactional
	@Override
	public void updateFactoryInfo(FactoryInfo factoryInfo) {
		factoryInfoDao.updateFactoryInfo(factoryInfo);
		
	}

}
