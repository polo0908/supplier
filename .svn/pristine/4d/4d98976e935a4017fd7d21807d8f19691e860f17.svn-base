package com.cbt.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.ProductionPhotoTimeline;

public interface ProductionPhotoTimelineService extends Serializable {

	
	
	/**
	 * 根据订单号查询图片信息     （上传时间分组）
	 * @param orderId
	 * @return
	 */
	public List<ProductionPhotoTimeline> queryByUploadDate(String orderId,String uploadDate);
	
    /**
     * 根据时间查询文档
     * @author polo
     * 2017年12月7日
     *
     */
    public List<ProductionPhotoTimeline> queryDocumentByUploadDate(String orderId,String uploadDate);
	
	
	
    /**
     * 批量插入对象
     * @param list
     */
    public void insert(List<ProductionPhotoTimeline> list);
    
    /**
     * 根据订单号查询后 上传时间分组
     * @author polo
     * 2017年6月2日
     *
     */
    public List<ProductionPhotoTimeline> queryByOrderIdGroupByDate(String orderId);
	
	
	/**
	 * 根据上传时间查询图片(删除后查询)
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
	public List<ProductionPhotoTimeline> queryByUploadDate(String orderId,String uploadDate,Integer id) throws Exception;
	
	
	/**
	 * 插入图片后查询
	 * @param orderId
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	public Map<Object,Object> insert(String orderId,List<ProductionPhotoTimeline> list) throws Exception;
	
	
	   
   /**
    * 根据时间批量更新备注
    * @param list
    */
   public void updateRemarkBatch(List<ProductionPhotoTimeline> list);
   
   /**
    * 根据订单号查询  （是否存在文档）
    * @Title queryByOrderAndStatus 
    * @Description TODO
    * @return
    * @return ProductionPhotoTimeline
    */
   public ProductionPhotoTimeline queryByOrderAndStatus(String orderId,String uploadDate);
   
   
   
   /**
    * 更新周报
    * @Title updateDocumentPath 
    * @Description TODO
    * @param ProductionPhotoTimeline
    * @return void
    */
   public void updateDocumentPath(ProductionPhotoTimeline productionPhotoTimeline);
   
   
   /**
    * 根据id查询
    * @Title queryByOrderAndStatus 
    * @Description TODO
    * @param id
    * @return
    * @return ProductionPhotoTimeline
    */
   public ProductionPhotoTimeline queryById(Integer id);
   
   
   
   
}
