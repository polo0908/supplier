package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.Milestone;

public interface MilestoneDao extends Serializable {

	
	
	/**
	 * orderId查询
	 * @param orderId
	 * @return
	 */
	List<Milestone> queryByOrderId(String orderId);
	
	/**
	 * 单个id查询
	 * @param id
	 * @return
	 */
	Milestone queryById(Integer id);
	
	
	/**
	 * 新增里程碑
	 * @param milestone
	 */
	void insert(Milestone milestone);
	
	
	/**
	 * 更新里程碑
	 * @param milestone
	 */
	void updateMilestone(Milestone milestone);
	
	
	/**
	 * 根据id删除里程碑
	 * @param id
	 */
	void delete(Integer id);
	
	/**
	 * 根据里程碑名字查询
	 * @param milestoneName
	 */
	Milestone queryByName(@Param("orderId")String orderId,@Param("milestoneName")String milestoneName,@Param("expectedOrActually")Integer expectedOrActually);
	
}
