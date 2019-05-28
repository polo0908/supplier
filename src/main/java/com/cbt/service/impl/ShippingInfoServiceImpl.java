package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.ShippingInfoDao;
import com.cbt.entity.ShippingInfo;
import com.cbt.service.ShippingInfoService;

@Service
public class ShippingInfoServiceImpl implements ShippingInfoService {
	
	@Resource
	private ShippingInfoDao shippingInfoDao;

	@Transactional
	@Override
	public void insertShippingInfo(List<Object> list) {
		shippingInfoDao.insertShippingInfo(list);

	}

	@Override
	public ShippingInfo queryByUserId(String userid) {
		ShippingInfo shippingInfo = shippingInfoDao.queryByUserId(userid);
		return shippingInfo;
	}

	@Transactional
	@Override
	public void updateShippingInfo(ShippingInfo shippingInfo) {
		shippingInfoDao.updateShippingInfo(shippingInfo);
		
	}

	
	@Transactional
	@Override
	public void insertInfo(ShippingInfo shippingInfo) {
		shippingInfoDao.insertInfo(shippingInfo);
		
	}

}
