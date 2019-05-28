package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.LoginLogDao;
import com.cbt.entity.LoginLog;
import com.cbt.service.LoginLogService;

@Service
public class LoginLogServiceImpl implements LoginLogService {
    
	@Resource
	private LoginLogDao loginLogDao;
	@Override
	public LoginLog queryById(Integer id) {
		
		return loginLogDao.queryById(id);
	}
	@Override
	public List<LoginLog> querySuccessTop() {
		
		return loginLogDao.querySuccessTop();
	}
	@Override
	public List<LoginLog> queryFailTop() {
		
		return loginLogDao.queryFailTop();
	}
	@Override
	public Integer queryTotal1() {
		
		return loginLogDao.queryTotal1();
	}
	@Override
	public Integer queryTotal2() {
		// TODO Auto-generated method stub
		return loginLogDao.queryTotal2();
	}




}
