package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.UserRelationEmail;

public interface UserRelationEmailDao extends Serializable {
    
	/**
	 * 根据email查询userid（user_relation_email表）
	 * @param email
	 * @return
	 */
	UserRelationEmail queryUseridByEmail(String email);
	
	
	
    /**
     * 插入对象
     * @param 
     */
    void insert(UserRelationEmail userRelationEmail);
    
    
    /**
     * 根据userid查询邮箱
     * @param userid
     * @return
     */
    List<UserRelationEmail> queryByUserId(String userid);
    
    
    /**
     * 更新数据
     * @Title update 
     * @Description
     * @param userRelationEmail
     * @return void
     */
    void update(UserRelationEmail userRelationEmail);
    
    
	/**
	 * 批量插入用户
	 */
	void insertUserRelationEmail(List<Object> list);
}
