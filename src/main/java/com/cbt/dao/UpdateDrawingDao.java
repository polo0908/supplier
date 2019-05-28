package com.cbt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.ClientDrawings;
import com.cbt.entity.UpdateDrawing;





public interface UpdateDrawingDao {
    
	
	/**
	 * 根据id查询图纸
	 * @param id
	 * @return
	 */
	UpdateDrawing queryById(Integer id);

    /**
     * 根据图纸id查询图纸更新信息
     * @param drawingId
     * @return
     */
    List<UpdateDrawing> queryListByDrawingId(Integer drawingId);
    
    /**
     * 更新图纸信息
     * @param map
     */
    void updateDrawing(UpdateDrawing updateDrawing);   

    /**
     * 批量插入对象
     * @param ArrayList
     */
    void insertUpdateDrawings(List<UpdateDrawing> list);
    /**
     * 单条插入对象
     * @param 
     */
    void insertUpdateDrawing(UpdateDrawing updateDrawing);
    /**
     * 删除更新图纸数据
     * @param 
     */
    void deleteById(Integer drawingId);
    
    
}
