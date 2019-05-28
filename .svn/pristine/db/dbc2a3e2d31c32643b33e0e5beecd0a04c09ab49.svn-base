package com.cbt.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ReOrderDrawings;

public interface ReOrderDrawingsDao {
    
	/**
	 * 根据reOrderId查询订单关联表(reorder_drawing)
	 * @author polo
	 *
	 */
	List<ReOrderDrawings> queryReOrderDrawings(Integer reOrderId);
	
	
	/**
	 * 根据id查询图纸
	 * @param id
	 * @return
	 */
	ReOrderDrawings queryById(Integer id);
	
	/**
	 * 根据ids查询
	 * @param ids
	 * @return
	 */
	List<ReOrderDrawings> queryByIds(Integer[] ids);
	
	  /**
	    * 批量插入对象
	    * @param ArrayList
	    */
	void insertReOrderDrawings(List<ReOrderDrawings> list); 
	
	
	/**
	 * 单条插入对象
	 * @param eeOrderDrawings
	 */
	void insertReOrderDrawing(ReOrderDrawings reOrderDrawings);
	
    /**
     * 根据id删除图纸
     * @param id
     */
    void deleteReOrderDrawing(Integer id);
    
    /**
     * 更新图纸信息
     * @param map
     */
    void updateReOrderDrawing(ReOrderDrawings reOrderDrawings); 

}
