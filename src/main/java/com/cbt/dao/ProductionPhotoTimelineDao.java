package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.ProductionPhotoTimeline;

public interface ProductionPhotoTimelineDao extends Serializable {
   
	/**
	 * 根据里程碑ID查询图片信息
	 * @param milestoneId
	 * @return
	 */
	List<ProductionPhotoTimeline> queryByOrderId(String orderId);
	

    /**
     * 批量插入对象
     * @param list
     */
     void insert(List<ProductionPhotoTimeline> list);
     
     
     /**
      * 根据图片id删除
      */
     void delete(Integer id);
     
     
     
     /**
      * 根据订单号查询后 上传时间分组
      * @author polo
      * 2017年6月2日
      *
      */
     List<ProductionPhotoTimeline> queryByOrderIdGroupByDate(@Param("orderId")String orderId);
     
     
     /**
      * 根据时间查询图片
      * @author polo
      * 2017年6月2日
      *
      */
     List<ProductionPhotoTimeline> queryByUploadDate(@Param("orderId")String orderId,@Param("uploadDate")String uploadDate);
 
     
     /**
      * 根据时间查询文档
      * @author polo
      * 2017年12月7日
      *
      */
     List<ProductionPhotoTimeline> queryDocumentByUploadDate(@Param("orderId")String orderId,@Param("uploadDate")String uploadDate);
     
     
     
     /**
      * 根据时间批量更新备注
      * @param list
      */
     void updateRemarkBatch(List<ProductionPhotoTimeline> list);
     
     
     /**
      * 更新周报
      * @Title updateDocumentPath 
      * @Description TODO
      * @param ProductionPhotoTimeline
      * @return void
      */
     void updateDocumentPath(ProductionPhotoTimeline productionPhotoTimeline);
     
     
     /**
      * 根据订单号查询  （是否存在文档）
      * @Title queryByOrderAndStatus 
      * @Description TODO
      * @return
      * @return ProductionPhotoTimeline
      */
     ProductionPhotoTimeline queryByOrderAndStatus(@Param("orderId")String orderId,@Param("uploadDate")String uploadDate);
     
     /**
      * 根据id查询
      * @Title queryByOrderAndStatus 
      * @Description TODO
      * @param id
      * @return
      * @return ProductionPhotoTimeline
      */
     ProductionPhotoTimeline queryById(Integer id);
     
     
     
     
}
