package com.cbt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.BackUser;

public interface BackUserDao {

	/**
	 * 查询crm后台的用户信息
	 * 
	 * @param info
	 * @return
	 */
	public List<BackUser> queryBackUserList(@Param("info") String info, @Param("start") Integer start,
			@Param("pageSize") Integer pageSize,@Param("factoryId") String factoryId);

	/**
	 * 查询crm后台的用户总数
	 * 
	 * @param info
	 * @return
	 */
	public Integer queryBackUserTotal(@Param("info") String info,@Param("factoryId") String factoryId);

	/**
	 * 单个查询crm后台的用户信息
	 * 
	 * @param id
	 * @return
	 */
	public BackUser queryBackUserById(@Param("id") Integer id);
	/**
	 * 单个查询crm后台的用户信息
	 * 
	 * @param 
	 * @return
	 */
	public BackUser queryBackUserByName(@Param("userName") String userName);
	/**
	 * 单个查询crm后台的用户信息
	 * 
	 * @param 
	 * @return
	 */
	public BackUser queryBackUserByUserId(@Param("userId") String userId);
	
	
	/**
	 * 根据用户名和密码单个查询crm后台的用户信息
	 * @param username
	 * @param pwd
	 * @return
	 */
	public BackUser queryBackUserByNameAndPsw(@Param("username") String username ,@Param("pwd") String pwd);

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
	public void deleteBackUserById(@Param("id") Integer id);

}
