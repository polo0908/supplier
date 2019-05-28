package com.cbt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.MilestoneDao;
import com.cbt.dao.ProductionPhotoTimelineDao;
import com.cbt.entity.Milestone;
import com.cbt.entity.ProductionPhotoTimeline;
import com.cbt.service.MilestoneService;

@Service
public class MilestoneServiceImpl implements MilestoneService {
    
	@Resource
	private MilestoneDao milestoneDao;
	@Resource
	private ProductionPhotoTimelineDao productionPhotoTimelineDao;
	
	
	public List<Milestone> queryByOrderId(String orderId) {
		return milestoneDao.queryByOrderId(orderId);
	}


	@Transactional
	@Override
	public Map<Object,Object> insert(Milestone milestone,String expectedOrActually,String orderId)throws Exception{
    	if(!(expectedOrActually == null || "".equals(expectedOrActually))){
    		milestone.setExpectedOrActually(Integer.parseInt(expectedOrActually));	
    	}
    	Milestone milestone1 = milestoneDao.queryByName(orderId, milestone.getMilestoneName(), milestone.getExpectedOrActually());
    	if(milestone1 != null){
    		milestoneDao.updateMilestone(milestone);
    	}else{
    		milestoneDao.insert(milestone);	
    	}	
		Map<Object,Object> map = new HashMap<Object, Object>();
		List<Milestone> milestones = milestoneDao.queryByOrderId(orderId);	 
		map.put("milestones", milestones);
		
		 return map;
	}

    @Transactional
	@Override
	public void updateMilestone(Milestone milestone){
	
		milestoneDao.updateMilestone(milestone);		
	}


	public Milestone queryById(Integer id) {
		return milestoneDao.queryById(id);
	}

    
	@Transactional
	@Override
	public List<Milestone> queryByOrderId(String orderId, Integer id) {
        if(id == null || "".equals(id)){
        	throw new RuntimeException("未获取到id");
        }
        if(orderId == null || "".equals(orderId)){
        	throw new RuntimeException("未获取到订单信息");
        }
		milestoneDao.delete(id);
        
		return milestoneDao.queryByOrderId(orderId);
	}


	@Override
	public Milestone queryByName(String orderId, String milestoneName,Integer expectedOrActually) {
		return milestoneDao.queryByName(orderId, milestoneName, expectedOrActually);
	}

}
