package com.cbt.service;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.UserRelationEmail;

public interface UserRelationEmailService extends Serializable {

	
	
    
	/**
	 * 根据email查询userid（user_relation_email表）
	 * @param email
	 * @return
	 */
	public UserRelationEmail queryUseridByEmail(String email);
	
	
	
    /**
     * 插入对象
     * @param
     */
	public List<UserRelationEmail> insert(String userid,String email)throws Exception;
    
    
    /**
     * 根据userid查询邮箱
     * @param userid
     * @return
     */
	public List<UserRelationEmail> queryByUserId(String userid)throws Exception;
	
	
	
	/**
	 * 批量插入用户
	 */
	public void insertUserRelationEmail(List<Object> list);
}
