package com.cbt.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.BackUser;

public interface BackUserService {

	/**
	 * 查询crm后台的用户信息
	 * 
	 * @param info
	 * @return
	 */
	public List<BackUser> queryBackUserList(String info, Integer start, Integer pageSize,String factoryId);

	/**
	 * 查询crm后台的用户总数
	 * 
	 * @param info
	 * @return
	 */
	public Integer queryBackUserTotal(String info,String factoryId);

	/**
	 * 单个查询crm后台的用户信息
	 * 
	 * @param id
	 * @return
	 */
	public BackUser queryBackUserById(Integer id);
	
	/**
	 * 单个查询crm后台的用户信息
	 * 
	 * @param id
	 * @return
	 */
	public BackUser queryBackUserByName(String userName);
	
	/**
	 * 单个查询crm后台的用户信息
	 * 
	 * @param 
	 * @return
	 */
	public BackUser queryBackUserByUserId(String userId);

	/**
	 * 插入crm后台用户信息
	 * 
	 * @param backUser
	 */
	public void insertBackUser(BackUser backUser);

	/**
	 * 修改crm后台用户信息
	 * 
	 * @param backUser
	 */
	public void updateBackUser(BackUser backUser);

	/**
	 * 删除crm后台用户信息
	 * 
	 * @param id
	 */
	public void deleteBackUserById(Integer id);

	/**
	 * 登录验证
	 * 
	 * @param userid
	 *            用户id
	 * @param pwd
	 *            密码
	 * @return 登录成功时候返回用户的信息
	 * @throws Exception
	 *             用户名或密码错误、用户名或密码为空
	 */
	BackUser login(String userid, String pwd) throws Exception;

}
