package com.cbt.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.User;
import com.cbt.entity.UserRelationEmail;

public interface UserService {

	/**
	 * 批量插入用户
	 */
	public void insertUser(List<Object> list);
	
	/**
	 * 单个插入用户
	 */
	public void insertUserByAdmin(User user);

	/**
	 * 根据参数查询匹配的用户
	 * 
	 * @param info
	 * @return
	 */
	public List<User> queryUserList(String info,Integer start,Integer pageSize,String salesId,String factoryId);

	/**
	 * 根据参数查询匹配的用户的总数
	 * 
	 * @param info
	 * @return
	 */
	public Integer queryUserTotal(String info,String salesId,String factoryId);
	/**
	 * 根据参数查询匹配的用户
	 * 
	 * @param info
	 * @return
	 */
	public List<User> queryUserListAdmin(String info,Integer start,Integer pageSize,String salesId,String factoryId);
	
	/**
	 * 根据参数查询匹配的用户的总数
	 * 
	 * @param info
	 * @return
	 */
	public Integer queryUserTotalAdmin(String info,String salesId,String factoryId);
	
	/**
	 * 查询所有用户
	 * 
	 * @param info
	 * @return
	 */
	public List<User> queryUserBySalesId(String salesId,String factoryId);
	
	/**
	 * 查询所有用户(管理员查询)
	 * 
	 * @param info
	 * @return
	 */
	public List<User> queryUserBySalesIdAdmin(String salesId,String factoryId);
	
	
	/**
	 * 根据客户名查询(返回客户和客户订单)
	 * @param userid
	 * @return
	 */	  
	public Map<Object,Object> queryByUser(String userid)throws Exception;
	
	/**
	 * 根据客户名查询
	 * @param userid
	 * @return
	 */	  
	public User queryByUserId(String userid);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public User queryById(Integer id);
	
	/**
	 * 根据登录邮箱查询
	 * @param loginEmail
	 * @return
	 */	  
	public User queryByLoginMail(String loginEmail);
	
	
	/**
	 * 更新user数据
	 * @param user
	 * @return
	 */	  
	public void updateCustomer(User user);
	
	

	/**
	 * 批量插入用户
	 */
	public void insertUser(List<Object> list,List<Object> list1,List<Object> list2,List<Object> list4,List<Object> relationEmails)throws Exception;
	
	
	/**
	 * 查询最近有修改订单的30个客户
	 * @Title queryUserByOrderUpdate 
	 * @Description TODO
	 * @return
	 * @return User
	 */
	public List<User> queryUserByOrderUpdate();
	
	
	/**
	 * 查询最近有修改订单的30个客户
	 * @Title queryByOrder 
	 * @Description TODO
	 * @return
	 * @return User
	 */
	public List<User> queryUserByInvitation();
	
	
	/**
	 * 查询最近登录的30个客户
	 * @Title queryUserByLogin 
	 * @Description TODO
	 * @return
	 * @return User
	 */
	public List<User> queryUserByLogin();
	
}
