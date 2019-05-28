package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.UserFactoryRelationDao;
import com.cbt.entity.UserFactoryRelation;
import com.cbt.service.UserFactoryRelationService;
@Service


public class UserFactoryRelationServiceImpl implements UserFactoryRelationService {
    @Resource
	private UserFactoryRelationDao userFactoryRelationDao;
	
	public List<UserFactoryRelation> queryByFactoryId(String factoryId) {
		
		return userFactoryRelationDao.queryByFactoryId(factoryId);
	}

	@Override
	public List<UserFactoryRelation> queryByUserid(String userid) {

		return userFactoryRelationDao.queryByUserid(userid);
	}

	@Override
	public void insert(UserFactoryRelation factoryUserRelation) {
		userFactoryRelationDao.insert(factoryUserRelation);

	}

	@Override
	public int totalFactory(String userid) {
		return userFactoryRelationDao.totalFactory(userid);
	}

}
