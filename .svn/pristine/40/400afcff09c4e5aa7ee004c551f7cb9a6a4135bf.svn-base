package com.cbt.service;

import java.util.List;
import java.util.Map;

import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.UpdateDrawing;

public interface UpdateDrawingService {
	/**
	 * 根据id查询图纸
	 * @param id
	 * @return
	 */
	public UpdateDrawing queryById(Integer id);

    /**
     * 根据图纸id查询图纸更新信息
     * @param drawingId
     * @return
     */
	public List<UpdateDrawing> queryListByDrawingId(Integer drawingId);
    
    /**
     * 更新图纸信息
     * @param map
     */
	public void updateDrawing(UpdateDrawing updateDrawing);   

    /**
     * 批量插入对象
     * @param ArrayList
     */
	public void insertUpdateDrawings(List<UpdateDrawing> list);
	
    /**
     * 单条插入对象
     * @param 
     */
    public void insertUpdateDrawing(UpdateDrawing updateDrawing);
    
    
    /**
     * 删除更新图纸数据
     * @param 
     */
    public void deleteById(Integer drawingId);
	
}
