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
import com.cbt.entity.ProductionPhotoTimeline;
import com.cbt.service.ProductionPhotoTimelineService;


@Service
public class ProductionPhotoTimelineServiceImpl implements  ProductionPhotoTimelineService{

	private static final long serialVersionUID = 1L;
	@Resource
	private ProductionPhotoTimelineDao productionPhotoTimelineDao;
	@Resource
	private MilestoneDao milestoneDao;
	
	
	@Override
	public List<ProductionPhotoTimeline> queryByUploadDate(String orderId,String uploadDate) {
		return productionPhotoTimelineDao.queryByUploadDate(orderId,uploadDate);
	}


	@Transactional
	@Override
	public void insert(List<ProductionPhotoTimeline> list) {
		productionPhotoTimelineDao.insert(list);
		
	}

      
	@Transactional
	@Override
	public List<ProductionPhotoTimeline> queryByUploadDate(String orderId,String uploadDate, Integer id) throws Exception {
		
		if(id == null || "".equals(id)){
           throw new Exception("未获取到id");
		}else{
			productionPhotoTimelineDao.delete(id);
		}
			
		return productionPhotoTimelineDao.queryByUploadDate(orderId, uploadDate);
	}

	
    @Transactional
	@Override
	public Map<Object,Object> insert(String orderId, List<ProductionPhotoTimeline> list) throws Exception{
		
        if(list.size() == 0){
        	
        }else{
        	productionPhotoTimelineDao.insert(list);
        }
        
        Map<Object,Object> map = new HashMap<Object, Object>();
        List<ProductionPhotoTimeline> list1 = productionPhotoTimelineDao.queryByOrderIdGroupByDate(orderId);
        List<List<ProductionPhotoTimeline>> list2 = new ArrayList<List<ProductionPhotoTimeline>>();
        for (ProductionPhotoTimeline productionPhotoTimeline : list1) {
        	if(productionPhotoTimeline.getIsDocument() == 0){
	        	List<ProductionPhotoTimeline> productionPhoto = productionPhotoTimelineDao.queryByUploadDate(orderId, productionPhotoTimeline.getUploadDate());
	        	if(productionPhoto != null && productionPhoto.size() !=0){
	            	list2.add(productionPhoto);	
	        	}
        	}else if(productionPhotoTimeline.getIsDocument() == 1){
	        	List<ProductionPhotoTimeline> document = productionPhotoTimelineDao.queryDocumentByUploadDate(orderId, productionPhotoTimeline.getUploadDate());
	        	if(document != null && document.size() !=0){
	        		list2.add(document);	
	        	}
        	}
		}
        map.put("productionPhotoes", list1);       
        map.put("productionPhotoTimelines", list2);       
        
		return map;
	}


	@Override
	public List<ProductionPhotoTimeline> queryByOrderIdGroupByDate(String orderId) {
		return productionPhotoTimelineDao.queryByOrderIdGroupByDate(orderId);
	}


	@Override
	public void updateRemarkBatch(List<ProductionPhotoTimeline> list) {
		productionPhotoTimelineDao.updateRemarkBatch(list);
		
	}


	@Override
	public ProductionPhotoTimeline queryByOrderAndStatus(String orderId,String uploadDate) {
		return productionPhotoTimelineDao.queryByOrderAndStatus(orderId,uploadDate);
	}


	@Override
	public void updateDocumentPath(
			ProductionPhotoTimeline productionPhotoTimeline) {
		productionPhotoTimelineDao.updateDocumentPath(productionPhotoTimeline);
		
	}


	@Override
	public List<ProductionPhotoTimeline> queryDocumentByUploadDate(
			String orderId, String uploadDate) {
		return productionPhotoTimelineDao.queryDocumentByUploadDate(orderId, uploadDate);
	}


	@Override
	public ProductionPhotoTimeline queryById(Integer id) {
		return productionPhotoTimelineDao.queryById(id);
	}
	
	
	

}
