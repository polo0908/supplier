package com.cbt.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.ClientDrawings;





public interface ClientDrawingsDao {
    
	
    /**
     * 根据订单号查询图纸信息
     * @param orderId
     * @return
     */
    List<ClientDrawings> queryListByOrderId(String orderId);
    /**
     * 更新图纸信息
     * @param map
     */
    void updateClientDrawings(ClientDrawings clientDrawing);   

    /**
     * 批量插入对象
     * @param ArrayList
     */
    void insertClientDrawings(List<Object> list);
    
    /**
     * 单条插入对象
     */
    void insertClientDrawing(ClientDrawings clientDrawing);
    
    /**
     * 根据id删除图纸
     * @param id
     */
    void deleteClientDrawing(Integer id);
    
    /**
     * 根据id查询
     * @param id
     * @return
     */
    ClientDrawings queryById(Integer id);
    
    /**
     * 批量插入对象
     * @param ArrayList
     */
   void insertDrawings(ArrayList<Map<Object, Object>> ArrayList);  
    
}
