package com.cbt.entity;

import java.io.Serializable;
import java.text.ParseException;

import com.cbt.util.DateFormat;

/**
* @ClassName: update_drawing 
* @Description: 客户图纸信息
 */
public class UpdateDrawing implements Serializable{
	

	private Integer id;
	private Integer drawingId;//图纸名
	private String drawingName;//图纸名称
	private String drawingPath;//图纸路径
	private String updateTime;//上传时间
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDrawingId() {
		return drawingId;
	}
	public void setDrawingId(Integer drawingId) {
		this.drawingId = drawingId;
	}
	public String getDrawingName() {
		return drawingName;
	}
	public void setDrawingName(String drawingName) {
		this.drawingName = drawingName;
	}
	public String getDrawingPath() {
		return drawingPath;
	}
	public void setDrawingPath(String drawingPath) {
		this.drawingPath = drawingPath;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		try {
			this.updateTime = DateFormat.formatDate2(updateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.updateTime = updateTime;
		}
		
	}
	@Override
	public String toString() {
		return "UpdateDrawing [id=" + id + ", drawingId=" + drawingId
				+ ", drawingName=" + drawingName + ", drawingPath="
				+ drawingPath + ", updateTime=" + updateTime + "]";
	}

    
    
	


	

	
	

	
	
	
}
