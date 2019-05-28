package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.FactoryUserRelationDao;
import com.cbt.entity.FactoryUserRelation;
import com.cbt.service.FactoryUserRelationService;
@Service
public class FactoryUserRelationServiceImpl implements FactoryUserRelationService {
    @Resource
	private FactoryUserRelationDao factoryUserRelationDao;
	
	public List<FactoryUserRelation> queryByFactoryId(String factoryId) {
		
		return factoryUserRelationDao.queryByFactoryId(factoryId);
	}

	@Override
	public List<FactoryUserRelation> queryByUserid(String userid) {

		return factoryUserRelationDao.queryByUserid(userid);
	}

	@Override
	public void insert(FactoryUserRelation factoryUserRelation) {
		factoryUserRelationDao.insert(factoryUserRelation);

	}

	@Override
	public FactoryUserRelation queryByFactoryIdAndUserid(String factoryId,String userid) {
		return factoryUserRelationDao.queryByFactoryIdAndUserid(factoryId, userid);
	}

	@Override
	public void updateRemark(FactoryUserRelation factoryUserRelation) {
		factoryUserRelationDao.updateRemark(factoryUserRelation);
		
	}

	@Override
	public int queryCount(String factoryId, String userid) {
		return factoryUserRelationDao.queryCount(factoryId, userid);
	}

}
