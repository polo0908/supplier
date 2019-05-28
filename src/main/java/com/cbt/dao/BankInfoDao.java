package com.cbt.dao;

import java.util.List;

import com.cbt.entity.BankInfo;

public interface BankInfoDao {

	/**
	 * 查询所有银行
	 * @return
	 */
	List<BankInfo> queryBankInfo();
	
	/**
	 * 根据id查询银行（从0开始）
	 * @param id
	 * @return
	 */
	BankInfo queryById(Integer id);
}
