package com.cbt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.UpdateDrawing;

public interface ClientDrawingsService {
	
    /**
     * 根据订单编号查询客户图纸信息列表
     * @param orderId
     * @return
     */	
	public List<ClientDrawings> queryListByOrderId(String orderId);
	
	/**
	 * 更新图纸信息
	 * @param clientDrawings
	 */
	public List<ClientDrawings> updateClientDrawings(ClientDrawings clientDrawing,UpdateDrawing updateDrawing)throws Exception;
	
    /**
     * 批量插入对象
     * @param ArrayList
     */
	public void insertClientDrawings(List<Object> list); 
	
    /**
     * 单条插入对象(更新图纸表)
     */
    public List<ClientDrawings> insertClientDrawing(ClientDrawings clientDrawing, UpdateDrawing updateDrawing,String orderId) throws Exception;
    
    
    public void insertClientDrawing(ClientDrawings clientDrawing);
    
    /**
     * 根据id删除图纸
     * @param id
     */
    public List<ClientDrawings> deleteClientDrawing(Integer id,String orderId)throws Exception;
    
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public ClientDrawings queryById(Integer id);
    
    /**
     * 批量插入对象
     * @param ArrayList
     */
    public void insertDrawings(ArrayList<Map<Object, Object>> ArrayList);
	
}
