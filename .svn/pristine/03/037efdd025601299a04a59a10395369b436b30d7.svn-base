package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.EmailAlertInfoDao;
import com.cbt.entity.EmailAlertInfo;
import com.cbt.service.EmailAlertInfoService;

@Service
public class EmailAlertInfoServiceImpl implements EmailAlertInfoService {
    
	@Resource
	private EmailAlertInfoDao emailAlertInfoDao;
	@Override
	public EmailAlertInfo queryById(Integer id) {
		return emailAlertInfoDao.queryById(id);
	}

	@Override
	public List<EmailAlertInfo> queryAll() {
		return emailAlertInfoDao.queryAll();
	}

}
