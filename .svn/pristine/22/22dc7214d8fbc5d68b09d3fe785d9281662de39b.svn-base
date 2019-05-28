package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.BankInfoDao;
import com.cbt.entity.BankInfo;
import com.cbt.service.BankInfoService;
@Service

public class BankInfoServiceImpl implements BankInfoService {
   
	@Resource
	private BankInfoDao bankInfoDao;

	@Override
	public List<BankInfo> queryBankInfo() {
		List<BankInfo> list = bankInfoDao.queryBankInfo();
		return list;
	}

	@Override
	public BankInfo queryById(Integer id) {
		BankInfo bankInfo = bankInfoDao.queryById(id);
		return bankInfo;
	}


}
