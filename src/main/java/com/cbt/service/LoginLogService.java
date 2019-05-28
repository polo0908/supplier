package com.cbt.service;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.LoginLog;

public interface LoginLogService extends Serializable {

	
	/**
	 * 根据id查询订单
	 * @param id
	 * @return
	 */
	public LoginLog queryById(Integer id);	   
    
    /**
     * 查询最近登录100用户
     * @return
     */
	public List<LoginLog> querySuccessTop();
	
	/**
	 * 查询最近登录失败100用户
	 * @return
	 */
	public List<LoginLog> queryFailTop();
	
	/**
	 * 查询登录成功的个数
	 * @return
	 */
	public Integer queryTotal1();
	/**
	 * 查询登录失败的个数
	 * @return
	 */
	public Integer queryTotal2();
}
