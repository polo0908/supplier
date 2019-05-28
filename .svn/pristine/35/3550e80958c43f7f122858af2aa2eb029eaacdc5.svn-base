package com.cbt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.User;

public interface UserDao {

	/**
	 * 批量插入用户
	 */
	void insertUser(List<Object> list);
	
	/**
	 * 单个插入用户
	 */
	void insertUserByAdmin(User user);

	/**
	 * 根据参数查询匹配的用户
	 * 
	 * @param info
	 * @return
	 */
	List<User> queryUserList(@Param("info") String info, @Param("start") Integer start,
			@Param("pageSize") Integer pageSize, @Param("salesId") String salesId,@Param("factoryId") String factoryId);
	
	
	/**
	 * 查询所有用户
	 * 
	 * @param info
	 * @return
	 */
	List<User> queryUserBySalesId(@Param("salesId") String salesId,@Param("factoryId") String factoryId);
	
	/**
	 * 查询所有用户(管理员查询)
	 * 
	 * @param info
	 * @return
	 */
	List<User> queryUserBySalesIdAdmin(@Param("salesId") String salesId,@Param("factoryId") String factoryId);
	

	/**
	 * 根据参数查询匹配的用户的总数
	 * 
	 * @param info
	 * @return
	 */
	Integer queryUserTotal(@Param("info") String info,@Param("salesId") String salesId,@Param("factoryId") String factoryId);
	/**
	 * 根据参数查询匹配的用户(管理员查询)
	 * 
	 * @param info
	 * @return
	 */
	List<User> queryUserListAdmin(@Param("info") String info, @Param("start") Integer start,
			@Param("pageSize") Integer pageSize, @Param("salesId") String salesId,@Param("factoryId") String factoryId);
	
	/**
	 * 根据参数查询匹配的用户的总数(管理员查询)
	 * 
	 * @param info
	 * @return
	 */
	Integer queryUserTotalAdmin(@Param("info") String info,@Param("salesId") String salesId,@Param("factoryId") String factoryId);

	/**
	 * 根据UserId查询
	 * 
	 * @param userid
	 * @return
	 */
	User queryByUserId(String userid);
	
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	User queryById(Integer id);
	
	/**
	 * 根据登录邮箱查询
	 * @param loginEmail
	 * @return
	 */	  
	User queryByLoginMail(String loginEmail);
	/**
	 * 更新user数据
	 * @param user
	 * @return
	 */	  
	void updateCustomer(User user);
	
	
	
	/**
	 * 查询最近有修改订单的30天的客户
	 * @Title queryUserByOrderUpdate 
	 * @Description TODO
	 * @return
	 * @return User
	 */
	List<User> queryUserByOrderUpdate();
	
	
	/**
	 * 查询最近邀请的30天的客户
	 * @Title queryByOrder 
	 * @Description TODO
	 * @return
	 * @return User
	 */
	List<User> queryUserByInvitation();
	
	
	/**
	 * 查询最近登录的30天的客户
	 * @Title queryUserByLogin 
	 * @Description TODO
	 * @return
	 * @return User
	 */
	List<User> queryUserByLogin();
}
