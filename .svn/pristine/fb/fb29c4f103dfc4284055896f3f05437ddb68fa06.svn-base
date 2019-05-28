package com.cbt.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.Milestone;

public interface MilestoneService extends Serializable {
	/**
	 * 单个orderId查询
	 * @param orderId
	 * @return
	 */
	public List<Milestone> queryByOrderId(String orderId);
	
	/**
	 * 新增里程碑
	 * @param milestone
	 */
	public Map<Object,Object> insert(Milestone milestone,String expectedOrActually,String orderId)throws Exception;
	
	
	/**
	 * 更新里程碑
	 * @param milestone
	 */
   public void updateMilestone(Milestone milestone);
   
   
	/**
	 * 单个id查询
	 * @param id
	 * @return
	 */
   public Milestone queryById(Integer id);
   
	/**
	 * 单个orderId查询(删除后查询)
	 * @param orderId
	 * @return
	 */
	public List<Milestone> queryByOrderId(String orderId,Integer id);
	
	
	/**
	 * 根据里程碑名字查询
	 * @param milestoneName
	 */
	public Milestone queryByName(String orderId,String milestoneName,Integer expectedOrActually);
}
